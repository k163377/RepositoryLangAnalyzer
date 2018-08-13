package com.wrongwrong.repositorylanganalyzer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.getValuesFromSplittedJson
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.makeRankOfLangs
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var reps: ArrayList<String>
    lateinit var langs: ArrayList<String>
    lateinit var descriptions: ArrayList<String>

    //ボタン押したときの挙動設定
    private fun initButton(){
        var input = findViewById<EditText>(R.id.inputAccount)
        var b = findViewById<Button>(R.id.launchButton)
        b.setOnClickListener {
            //キーボードの非表示
            var imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            Toast.makeText(this, "開始", Toast.LENGTH_LONG).show()
            var context = this
            //ボタンを無効化しjson取得など
            b.isEnabled = false
            launch(UI) {
                try {
                    var jsonArr = GitHubUtil.getJsonFromURL(URL("https://api.github.com/users/${input.text}/repos")).await().split(",\"")
                    reps = getValuesFromSplittedJson(jsonArr, "full_name\":")
                    langs = getValuesFromSplittedJson(jsonArr, "language\":")
                    for(i in 0 until langs.count()) if(langs[i] == "null") langs[i] = "Other Language"
                    descriptions = getValuesFromSplittedJson(jsonArr, "description\":")

                    if(langs.count() != 0) {
                        var rankOfLangs = makeRankOfLangs(langs)

                        var lv = findViewById<ListView>(R.id.listView)
                        lv.adapter = LanguageAdapter(context, rankOfLangs, langs.count())
                        Toast.makeText(context, "正常に取得を完了しました。", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, "リポジトリを取得できませんでした。", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    Toast.makeText(context, "リポジトリを見つけられませんでした。ユーザー名が正しいか確認してください。", Toast.LENGTH_SHORT).show()
                } catch (e: NetworkOnMainThreadException) {
                    Toast.makeText(context, "ネットワークに接続できませんでした。", Toast.LENGTH_SHORT).show()
                } finally {
                    b.isEnabled = true
                }

                test()
            }
        }
    }

    fun test(){
        var reposCall = getReposCall("k163377")
        var arr: List<Repo>?
        reposCall.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                try{
                    arr = response!!.body()
                    for(repo in arr!!) Log.d("Repos", repo.full_name)
                    // Log.d("onResponse", arr!![0].full_name)
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Log.d("onFailure", "")
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButton()
        findViewById<ListView>(R.id.listView).setOnItemClickListener{parent, v, position, id ->
            var intent = Intent(this.applicationContext, RepositoryActivity::class.java)
            intent.putExtra("language", (parent.getItemAtPosition(position) as Pair<String, Int>).first)
            intent.putExtra("repositories", reps)
            intent.putExtra("languages", langs)
            intent.putExtra("descriptions", descriptions)
            startActivity(intent)
        }
    }
}
