package com.wrongwrong.repositorylanganalyzer

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.io.FileNotFoundException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b: Button = this.findViewById(R.id.launchButton)
        b.setOnClickListener {
            Toast.makeText(this, "開始", Toast.LENGTH_LONG).show()
            try{
                b.isEnabled = false
                var a = async{
                    GitHubUtil.getJsonFromURL(URL("https://api.github.com/users/k163377/repos"))
                }.await()
                Toast.makeText(this, "正常終了", Toast.LENGTH_LONG).show()
            }catch (e: FileNotFoundException){
                Toast.makeText(this, "リポジトリを見つけられませんでした。ユーザー名が正しいか確認してください。", Toast.LENGTH_SHORT).show()
            }catch (e: NetworkOnMainThreadException){
                Toast.makeText(this, "ネットワークに接続できませんでした。", Toast.LENGTH_SHORT).show()
            }finally {
                b.isEnabled = true
            }
        }

    }
}
