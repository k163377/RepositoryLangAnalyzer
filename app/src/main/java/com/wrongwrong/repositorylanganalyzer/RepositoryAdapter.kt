package com.wrongwrong.repositorylanganalyzer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RepositoryAdapter (val context: Context,
                         val language: String,
                         repositories: Array<Repo>,
                         val colorId: Int) : BaseAdapter() {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val repDescPairs = ArrayList<Pair<String, String>>()

    init {
        for(repo in repositories)
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
        val rep_name = view.findViewById<TextView>(R.id.rep_name)
        rep_name.text = repDescPairs[position].first
        rep_name.background = ColorDrawable(ContextCompat.getColor(context, colorId))

        val description = view.findViewById<TextView>(R.id.description)
        description.text = repDescPairs[position].second
        if(position % 2 == 1) description.background = ColorDrawable(ContextCompat.getColor(context, R.color.colorLightGray))
        return view
    }
}