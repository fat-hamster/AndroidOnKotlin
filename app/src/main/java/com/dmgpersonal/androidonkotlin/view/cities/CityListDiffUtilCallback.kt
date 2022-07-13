package com.dmgpersonal.androidonkotlin.view.cities

import androidx.recyclerview.widget.DiffUtil
import com.dmgpersonal.androidonkotlin.model.Weather

class CityListDiffUtilCallback(private val oldList: List<Weather>, private val newList: List<Weather>): DiffUtil.Callback() {
//FIXME: Сделать DiffUtil
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition] // TODO: ?????????????

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}