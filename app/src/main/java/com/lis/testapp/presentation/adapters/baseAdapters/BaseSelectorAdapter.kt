package com.lis.testapp.presentation.adapters.baseAdapters


import android.view.View


abstract class BaseSelectorAdapter<VH : BaseSelectorAdapter.BaseSelectorViewHolder>(
    private val listItem: List<*>,
    private val idLayout: Int
) : BaseAdapter<VH>(listItem,idLayout) {
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = listItem[position]
        if(position == selectedItemPos)
            holder.selectedItem(item)
        else
            holder.unselectedItem(item)
        holder.bind(item)
    }

    abstract class BaseSelectorViewHolder(itemView: View) : BaseViewHolder(itemView) {

        abstract fun unselectedItem(item: Any?)

        abstract fun selectedItem(item: Any?)
    }
}
