package com.wrongwrong.repositorylanganalyzer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.widget.*
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.makeRankOfLangs
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var repositories: List<Repo>

    private fun setPartsEnabled(b: Boolean){
        this.launchButton.isEnabled = b
        this.listView.isEnabled = b
        this.inputAccount.isEnabled = b
    }

    //リポジトリを全て獲得してセットまでやる関数
    private fun getRepos(context: Context,
                         repoArray: ArrayList<Repo>,
                         page: Int
    ){
        //取得開始
        reposService.getRepos(
                inputAccount.text.toString(), page
        ).enqueue(object : Callback<List<Repo>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                val body: List<Repo>? = response!!.body()
                //結果が何も無かったならメッセージを出して終了
                if(body == null || (body.isEmpty() && repoArray.size == 0)){
                    Toast.makeText(context, R.string.faultGettingMessage, Toast.LENGTH_LONG).show()
                    setPartsEnabled(true)
                    return
                }

                //リポジトリが空でなければ追加
                if(body.isNotEmpty()){
                    repoArray.addAll(body)
                    //30以上有ればまだ取得しきれていない可能性がある
                    if(body.size == 100) {
                        getRepos(context, repoArray, page+1)
                        return
                    }
                }

                //後処理、リストに入れる
                val rankOfLangs = makeRankOfLangs(repoArray)
                val numColors = ArrayList<Int>()
                //取得したままだと都合が悪い文字列が有るので、変換
                for(p in rankOfLangs){
                    numColors.add(
                            resources.getIdentifier(
                                    p.first.replace(' ', '_')
                                            .replace('+', '_').replace('#', '_')
                                            .replace('-', '_').replace('\'', '_'),
                                    "color", packageName
                            )
                    )
                }
                //アダプタにセット
                listView.adapter = LanguageAdapter(context, rankOfLangs, repoArray.size, numColors)

                //取得結果のテキストをセット
                repSumText.text =
                        getString(R.string.successMessage)
                                .replace("[id]", inputAccount.text.toString())
                                .replace("[numOfRepos]", "${repoArray.size}")
                repSumText.visibility = View.VISIBLE

                repositories = repoArray.toList() //取得したリポジトリ全体は保存
                setPartsEnabled(true) //UIのロックを解除し終了
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Toast.makeText(context, "Network communication failed.\nPlease check the communication status.", Toast.LENGTH_LONG).show()
                setPartsEnabled(true)
            }
        })
    }

    //ロックした上でデータ取得を開始する、2箇所から呼び出すので関数化
    private fun getInformation(){
        //キーボードの非表示
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

        //パーツ類を無効化
        setPartsEnabled(false)

        //処理開始通知のトースト表示
        Toast.makeText(this, getText(R.string.startMessage), Toast.LENGTH_LONG).show()
        val context = this

        getRepos(context, ArrayList(), 1)
    }

    fun onClickLaunch(view: View){
        getInformation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)))

        listView.setOnItemClickListener{ parent, _, position, _ ->
            val intent = Intent(this.applicationContext, RepositoryActivity::class.java)
            intent.putExtra("language", (parent.getItemAtPosition(position) as Pair<String, Int>).first)
            intent.putExtra("repositories", repositories.toTypedArray())
            startActivity(intent)
        }

        inputAccount.setOnEditorActionListener { _, _, _ ->
            getInformation()
            return@setOnEditorActionListener true
        }
    }
}
