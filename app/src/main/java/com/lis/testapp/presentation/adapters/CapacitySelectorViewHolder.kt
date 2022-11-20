package com.lis.testapp.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lis.testapp.R

class CapacitySelectorViewHolder(capacityList: List<String>, idLayout: Int):
BaseAdapter<CapacitySelectorViewHolder.CapacitySelectorViewHolder>(capacityList,idLayout){

    inner class CapacitySelectorViewHolder(itemView: View): BaseViewHolder(itemView) {

        private val capacityText = itemView.findViewById<TextView>(R.id.capacity_text)
        private val capacityBackground = itemView.findViewById<ImageView>(R.id.background_block)

        override fun bind(item: Any?) {
            if (item == null) {
                //TODO("заглушка")
            } else {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if(item is String){
                capacityText.text = item + "Gb"
            }
        }

    }

    override fun createViewHolder(itemView: View): CapacitySelectorViewHolder {
        return CapacitySelectorViewHolder(itemView)
    }
}