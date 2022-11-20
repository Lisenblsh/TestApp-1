package com.lis.testapp.presentation.adapters

import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import com.lis.testapp.R


class ColorSelectorAdapter(colorList: List<String>, idLayout: Int) :
    BaseAdapter<ColorSelectorAdapter.ColorSelectorViewHolder>(colorList, idLayout) {

    inner class ColorSelectorViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val colorCircle = itemView.findViewById<ImageView>(R.id.color_circle)
        private val checkBox = itemView.findViewById<ImageView>(R.id.checkbox)

        override fun bind(item: Any?) {
            if (item == null) {
                //TODO("заглушка")
            } else {
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