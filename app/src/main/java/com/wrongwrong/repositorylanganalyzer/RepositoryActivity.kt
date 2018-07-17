package com.wrongwrong.repositorylanganalyzer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView

class RepositoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        var titleLang = intent.getStringExtra("language")
        var lv = findViewById<ListView>(R.id.repository_list)
        lv.adapter = RepositoryAdapter(this, titleLang, intent.extras["repositories"] as ArrayList<String>,
                intent.extras["languages"] as ArrayList<String>, intent.extras["descriptions"] as ArrayList<String>)

        title = titleLang
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent)))
    }
}