package com.wrongwrong.repositorylanganalyzer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView

class RepositoryActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        var titleLang = intent.getStringExtra("language")
        findViewById<TextView>(R.id.title_language).text = titleLang
        var lv = findViewById<ListView>(R.id.repository_list)
        lv.adapter = RepositoryAdapter(this, titleLang, intent.extras["repositories"] as ArrayList<String>,
                intent.extras["languages"] as ArrayList<String>, intent.extras["descriptions"] as ArrayList<String>)
    }
}