package com.wrongwrong.repositorylanganalyzer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class LanguageAdapter(context: Context, sortedList: List<Pair<String, Int>>) : BaseAdapter() {
    var cont = context
    var layoutInflater = cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var list = sortedList

    override fun getCount(): Int {
        return list.count()
    }

    override fun getItem(position: Int): Pair<String, Int> {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = layoutInflater.inflate(R.layout.language_item, parent, false)
        view.findViewById<TextView>(R.id.num).text = "${list[position].second}"
        view.findViewById<TextView>(R.id.language).text = list[position].first

        Log.d("${Log.DEBUG}", "ListCount${list.count()}")

        return view
    }
}