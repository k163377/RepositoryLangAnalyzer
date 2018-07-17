package com.wrongwrong.repositorylanganalyzer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.getLanguagesFromJSON
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.getRepsFromJSON
import com.wrongwrong.repositorylanganalyzer.GitHubUtil.Companion.makeRankOfLangs
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.FileNotFoundException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var input = findViewById<EditText>(R.id.inputAccount)
        var b = findViewById<Button>(R.id.launchButton)
        b.setOnClickListener {
            Toast.makeText(this, "開始", Toast.LENGTH_LONG).show()
            var context = this
            b.isEnabled = false
            launch(UI) {
                try {
                    var json = GitHubUtil.getJsonFromURL(URL("https://api.github.com/users/${input.text}/repos")).await()

                    //var reps = getRepsFromJSON(json)
                    var langs = getLanguagesFromJSON(json)
                    var rankOfLangs = makeRankOfLangs(langs)

                    var lv = findViewById<ListView>(R.id.listView)
                    lv.adapter = LanguageAdapter(context, rankOfLangs)
                    //Toast.makeText(context, "正常に取得を完了しました。", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    Toast.makeText(context, "リポジトリを見つけられませんでした。ユーザー名が正しいか確認してください。", Toast.LENGTH_SHORT).show()
                } catch (e: NetworkOnMainThreadException) {
                    Toast.makeText(context, "ネットワークに接続できませんでした。", Toast.LENGTH_SHORT).show()
                } finally {
                    b.isEnabled = true
                }
            }

        }
    }
}
