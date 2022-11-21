package com.lis.testapp.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.lis.testapp.R

class CapacitySelectorAdapter(capacityList: List<String>, idLayout: Int) :
    BaseSelectorAdapter<CapacitySelectorAdapter.CapacitySelectorViewHolder>(capacityList, idLayout) {

    inner class CapacitySelectorViewHolder(itemView: View) : BaseSelectorViewHolder(itemView) {

        private val capacityText = itemView.findViewById<TextView>(R.id.capacity_text)
        private val capacityBackground = itemView.findViewById<ImageView>(R.id.background_block)

        init {
            itemView.setOnClickListener {
                selectedItemPos = bindingAdapterPosition
                lastItemSelectedPos = if(lastItemSelectedPos == -1)
                    selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                clickListener.onItemClick(selectedItemPos)
                notifyItemChanged(selectedItemPos)
            }
        }

        override fun bind(item: Any?) {
            if (item != null) {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if (item is String) {
                capacityText.text = item + "Gb"
            }
        }


        override fun unselectedItem(item: Any?) {
            capacityText.setTextColor(
                itemView.resources.getColor(
                    R.color.grey,
                    itemView.resources.newTheme()
                )
            )
            capacityBackground.isVisible = false
        }

        override fun selectedItem(item: Any?) {
            capacityText.setTextColor(
                itemView.resources.getColor(
                    R.color.white,
                    itemView.resources.newTheme()
                )
            )
            capacityBackground.isVisible = true

        }

    }

    override fun createViewHolder(itemView: View): CapacitySelectorViewHolder {
        return CapacitySelectorViewHolder(itemView)
    }

}