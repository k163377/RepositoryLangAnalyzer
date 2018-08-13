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
    var repositories: List<Repo>? = null

    //ボタン押したときの挙動設定
    private fun initButton(){
        val input = findViewById<EditText>(R.id.inputAccount)
        val b = findViewById<Button>(R.id.launchButton)
        b.setOnClickListener {
            //キーボードの非表示
            val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            //処理開始通知のトースト表示
            Toast.makeText(this, "Start.", Toast.LENGTH_LONG).show()
            val context = this
            //ボタンを無効化しjson取得など
            b.isEnabled = false

            val reposCall = getReposCall(input.text.toString())
            reposCall.enqueue(object : Callback<List<Repo>> {
                override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                    try{
                        repositories = response!!.body()
                        if(repositories != null && repositories!!.isNotEmpty()){
                            val rankOfLangs = makeRankOfLangs(repositories)
                            val lv = findViewById<ListView>(R.id.listView)
                            lv.adapter = LanguageAdapter(context, rankOfLangs, repositories!!.count())
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Couldn't find any repositories.\n" +
                                    "The spelling is wrong or the public repository does not exist.", Toast.LENGTH_LONG).show()
                        }
                    }catch (e: IOException) {
                        Log.d("onResponse", "IOException")
                    }finally {
                        b.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                    Toast.makeText(context, "Network communication failed.\nPlease check the communication status.", Toast.LENGTH_LONG).show()
                    b.isEnabled = true
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButton()
        findViewById<ListView>(R.id.listView).setOnItemClickListener{parent, v, position, id ->
            var intent = Intent(this.applicationContext, RepositoryActivity::class.java)
            intent.putExtra("language", (parent.getItemAtPosition(position) as Pair<String, Int>).first)
            intent.putExtra("repositories", repositories!!.toTypedArray())
            startActivity(intent)
        }
    }
}
