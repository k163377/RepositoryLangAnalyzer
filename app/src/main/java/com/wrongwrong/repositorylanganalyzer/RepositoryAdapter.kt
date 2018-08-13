package com.wrongwrong.repositorylanganalyzer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RepositoryAdapter (context: Context,
                         language: String,
                         repositories: Array<Repo>) : BaseAdapter() {
    var cont = context
    var layoutInflater = cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var repDescPairs = ArrayList<Pair<String, String>>()

    init {
        for(repo in repositories!!)
            if(language == repo.language)
                repDescPairs.add(Pair(repo.full_name, repo.description) as Pair<String, String>)
    }

    override fun getCount(): Int {
        return repDescPairs.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Pair<String, String> {
        return repDescPairs[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.repository_item, parent, false)
        view.findViewById<TextView>(R.id.rep_name).text = repDescPairs[position].first
        view.findViewById<TextView>(R.id.description).text = repDescPairs[position].second

        return view
    }
}