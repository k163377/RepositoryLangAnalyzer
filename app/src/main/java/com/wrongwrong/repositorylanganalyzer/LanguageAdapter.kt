package com.wrongwrong.repositorylanganalyzer

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class LanguageAdapter(context: Context, sortedList: List<Pair<String, Int>>, numOfReps: Int, numColorIds: ArrayList<Int>) : BaseAdapter() {
    var cont = context
    var layoutInflater = cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var list = sortedList
    var sumOfReps = numOfReps.toDouble()
    private val numColors = numColorIds

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
        val numtext = view.findViewById<TextView>(R.id.num)
        numtext.text = "${list[position].second}"
        numtext.background = ColorDrawable(ContextCompat.getColor(cont, numColors[position]))

        view.findViewById<TextView>(R.id.language).text = "${list[position].first}"
        view.findViewById<TextView>(R.id.parcent).text = " ${String.format("%3.2f", ((list[position].second * 100).toDouble() / sumOfReps))}%"
        return view
    }
}