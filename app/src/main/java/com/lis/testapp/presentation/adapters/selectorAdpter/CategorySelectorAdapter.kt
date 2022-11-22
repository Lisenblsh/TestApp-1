package com.lis.testapp.presentation.adapters.selectorAdpter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.lis.domain.models.CategoryModel
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseSelectorAdapter

class CategorySelectorAdapter(categoryList: List<CategoryModel>, idLayout: Int) :
    BaseSelectorAdapter<CategorySelectorAdapter.CategorySelectorViewHolder>(
        categoryList,
        idLayout
    ) {

    inner class CategorySelectorViewHolder(itemView: View) : BaseSelectorViewHolder(itemView) {

        private val iconCategory = itemView.findViewById<ImageView>(R.id.icon_category)
        private val backgroundCategory = itemView.findViewById<CardView>(R.id.background_category)
        private val titleCategory = itemView.findViewById<TextView>(R.id.title_category)

        init {
            lastItemSelectedPos = 0
            selectedItemPos = 0
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
            if (item is CategoryModel) {
                ImageFun().setImage(item.iconCategoryId, iconCategory)
                titleCategory.text = item.titleCategory
            }
        }

        override fun selectedItem(item: Any?) {
            val orangeColor = getColor(R.color.orange)
            titleCategory.setTextColor(orangeColor)
            backgroundCategory.setCardBackgroundColor(orangeColor)
            iconCategory.setColorFilter(getColor(R.color.white))
        }

        override fun unselectedItem(item: Any?) {
            titleCategory.setTextColor(getColor(R.color.dark_blue))
            backgroundCategory.setCardBackgroundColor(getColor(R.color.white))
            iconCategory.setColorFilter(getColor(R.color.grey))
        }

        private fun getColor(color: Int) =
            itemView.resources.getColor(color, itemView.resources.newTheme())

    }

    override fun createViewHolder(itemView: View): CategorySelectorViewHolder {
        return CategorySelectorViewHolder(itemView)
    }
}