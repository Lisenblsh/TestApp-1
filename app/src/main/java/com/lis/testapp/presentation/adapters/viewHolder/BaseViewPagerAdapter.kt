package com.lis.testapp.presentation.adapters.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewPagerAdapter<VH : BaseViewPagerAdapter.BaseViewHolder>(
    private val listItem: List<*>,
    private val idLayout: Int
) :
    RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val view = LayoutInflater.from(parent.context).inflate(idLayout, parent, false)
        return createViewHolder(view)
    }

    abstract fun createViewHolder(itemView: View): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size


    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Any?)
    }

}
