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
    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var selectedRepositories = ArrayList<Repo>()

    init {
        for(repo in repositories)
            if(language == repo.language)
                selectedRepositories.add(repo)
    }

    override fun getCount(): Int {
        return selectedRepositories.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Repo {
        return selectedRepositories[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.item_repository, parent, false)
        val rep_name = view.findViewById<TextView>(R.id.rep_name)
        rep_name.text = selectedRepositories[position].full_name
        rep_name.background = ColorDrawable(ContextCompat.getColor(context, colorId))

        val description = view.findViewById<TextView>(R.id.description)
        description.text = selectedRepositories[position].description
        if(position % 2 == 1) description.background = ColorDrawable(ContextCompat.getColor(context, R.color.colorLightGray))
        return view
    }
}