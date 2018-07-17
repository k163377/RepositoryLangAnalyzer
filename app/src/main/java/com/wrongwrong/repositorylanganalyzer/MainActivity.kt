package com.wrongwrong.repositorylanganalyzer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
            val launch = launch(UI) {
                try {
                    var json = GitHubUtil.getJsonFromURL(URL("https://api.github.com/users/${input.text}/repos"))
                    Toast.makeText(context, json.await(), Toast.LENGTH_LONG).show()
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
