package com.wrongwrong.repositorylanganalyzer

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val titleLang = intent.getStringExtra("language")
        val repos = intent.extras["repositories"] as Array<Repo>
        val titleColor = resources.getIdentifier(
                titleLang.replace(' ', '_')
                        .replace('+', '_')
                        .replace('#', '_')
                        .replace('-', '_')
                        .replace('\'', '_'),
                "color", packageName
        )
        repository_list.adapter = RepositoryAdapter(this, titleLang, repos, if(titleColor == 0) R.color.colorWhite else titleColor)

        //タイトルを変更
        title = titleLang
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)))

        repository_list.setOnItemClickListener { parent, _, position, _ ->
            val uri = Uri.parse((parent.getItemAtPosition(position) as Repo).html_url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}