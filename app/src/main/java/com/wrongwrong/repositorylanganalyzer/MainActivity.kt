package com.wrongwrong.repositorylanganalyzer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.widget.*
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.makeRankOfLangs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var repositories: List<Repo>? = null
    //パーツ
    lateinit var launchButton: Button
    lateinit var listView: ListView
    lateinit var input: EditText
    lateinit var repSumText: TextView

    private fun setPartsEnabled(b: Boolean){
        this.launchButton.isEnabled = b
        this.listView.isEnabled = b
        this.input.isEnabled = b
    }

    private fun getInformations(){
        //キーボードの非表示
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

        //パーツ類を無効化
        setPartsEnabled(false)

        //処理開始通知のトースト表示
        Toast.makeText(this, getText(R.string.startMessage), Toast.LENGTH_LONG).show()
        val context = this

        val reposCall = getReposCall(input.text.toString()) //データを取得
        reposCall.enqueue(object : Callback<List<Repo>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                try{
                    repositories = response!!.body()
                    if(repositories != null && repositories!!.isNotEmpty()){
                        val rankOfLangs = makeRankOfLangs(repositories)
                        val numColors = ArrayList<Int>()
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
                        listView.adapter = LanguageAdapter(context, rankOfLangs, repositories!!.count(), numColors)
                        repSumText.text =
                                getString(R.string.successMessage)
                                        .replace("[id]", input.getText().toString())
                                        .replace("[numOfRepos]", "${repositories!!.count()}")
                        //repSumText.text = "Find ${repositories!!.count()} repositories."
                        repSumText.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(context, R.string.faultGettingMessage, Toast.LENGTH_LONG).show()
                    }
                }catch (e: IOException) {
                    Log.d("onResponse", "IOException")
                }finally {
                    setPartsEnabled(true)
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Toast.makeText(context, "Network communication failed.\nPlease check the communication status.", Toast.LENGTH_LONG).show()
                setPartsEnabled(true)
            }
        })
    }

    fun onClickLaunch(view: View){
        getInformations()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchButton = findViewById(R.id.launchButton)
        listView = findViewById(R.id.listView)
        input = findViewById(R.id.inputAccount)
        repSumText = findViewById(R.id.repSumText)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)))

        listView.setOnItemClickListener{parent, v, position, id ->
            val intent = Intent(this.applicationContext, RepositoryActivity::class.java)
            intent.putExtra("language", (parent.getItemAtPosition(position) as Pair<String, Int>).first)
            intent.putExtra("repositories", repositories!!.toTypedArray())
            startActivity(intent)
        }

        input.setOnEditorActionListener { v, actionId, keyEvent ->
            getInformations()
            return@setOnEditorActionListener true
        }
    }
}
