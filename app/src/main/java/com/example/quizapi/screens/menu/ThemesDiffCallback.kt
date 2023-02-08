package com.example.quizapi.screens.menu

import androidx.recyclerview.widget.DiffUtil
import com.example.quizapi.model.Theme

class ThemesDiffCallback(
    private val oldList: List<Theme>,
    private val newList: List<Theme>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}