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

class LanguageAdapter(val context: Context,
                      val sortedList: List<Pair<String, Int>>,
                      numOfReps: Int,
                      val numColorIds: ArrayList<Int>) : BaseAdapter() {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var sumOfReps = numOfReps.toDouble()

    override fun getCount(): Int {
        return sortedList.count()
    }

    override fun getItem(position: Int): Pair<String, Int> {
        return sortedList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.item_language, parent, false)

        val langText = view.findViewById<TextView>(R.id.language)
        langText.text = sortedList[position].first
        langText.background = ColorDrawable(ContextCompat.getColor(context, if(numColorIds[position] != 0) numColorIds[position] else R.color.colorWhite))

        val numtext = view.findViewById<TextView>(R.id.num)
        numtext.text = "${sortedList[position].second}"

        view.findViewById<TextView>(R.id.parcent).text = "${String.format("%3.2f", ((sortedList[position].second * 100).toDouble() / sumOfReps))}%"
        return view
    }
}