package com.example.quizapi.screens.menu

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.quizapi.R
import com.example.quizapi.model.Theme

class ThemesAdapter: RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {
    var count = 0
    var themeList = listOf<Theme>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var onItemClickListener: ((Theme) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_theme,parent,false)
        return ThemesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return themeList.size
    }

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) {
        Log.d("ShopListAdapter", "onBindViewHolder, count: ${++count}")
        val themeElem = themeList[position]
        holder.themeText.text = themeElem.themeName
        holder.themeCheckBox.isChecked = themeElem.isActive
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(themeElem)
        }

    }

    class ThemesViewHolder(itemView: View) : ViewHolder(itemView){
        val themeText = itemView.findViewById<TextView>(R.id.themeTextView)!!
        val themeCheckBox = itemView.findViewById<CheckBox>(R.id.themeCheckBox)!!
    }
}