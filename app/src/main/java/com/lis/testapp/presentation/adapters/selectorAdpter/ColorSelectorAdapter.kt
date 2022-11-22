package com.lis.testapp.presentation.adapters.selectorAdpter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseSelectorAdapter

class ColorSelectorAdapter(colorList: List<String>, idLayout: Int) :
    BaseSelectorAdapter<ColorSelectorAdapter.ColorSelectorViewHolder>(colorList, idLayout) {

    inner class ColorSelectorViewHolder(itemView: View) : BaseSelectorViewHolder(itemView) {

        private val colorCircle = itemView.findViewById<ImageView>(R.id.color_circle)
        private val checkBox = itemView.findViewById<ImageView>(R.id.checkbox)

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

        override fun unselectedItem(item: Any?) {
            checkBox.isVisible = false
        }

        override fun selectedItem(item: Any?) {
            checkBox.isVisible = true
        }

        override fun bind(item: Any?) {
            if (item != null) {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if (item is String) {
                val color = Color.parseColor(item)
                colorCircle.setColorFilter(color)
                val checkBoxColor = checkBoxColor(color)
                checkBox.setColorFilter(checkBoxColor)
            }
        }

        private fun checkBoxColor(color: Int): Int {
            val darkness =
                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                    color
                )) / 255
            val checkBoxColor = if (darkness < 0.5) {
                Color.parseColor("#000000")
            } else {
                Color.parseColor("#FFFFFF")
            }
            return checkBoxColor
        }

    }

    override fun createViewHolder(itemView: View): ColorSelectorViewHolder {
        return ColorSelectorViewHolder(itemView)
    }
}