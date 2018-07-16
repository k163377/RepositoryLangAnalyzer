package com.wrongwrong.repositorylanganalyzer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.io.FileNotFoundException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b =findViewById<Button>(R.id.launchButton)
        b.setOnClickListener {
            try{
                GitHubUtil.getJsonFromURL(URL("https://api.github.com/users/k163377/repos"))
            }catch (e: FileNotFoundException){
                Toast.makeText(this, "リポジトリを見つけられませんでした。ユーザー名が正しいか確認してください。", Toast.LENGTH_SHORT)
            }
        }

    }
}
