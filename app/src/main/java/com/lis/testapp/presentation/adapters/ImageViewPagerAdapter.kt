package com.lis.testapp.presentation.adapters

import android.view.View
import android.widget.ImageView
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter

class ImageViewPagerAdapter(imageList: List<String>, idLayout: Int) :
    BaseAdapter<ImageViewPagerAdapter.ImageViewHolder>(imageList, idLayout) {
    inner class ImageViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val imageProduct = itemView.findViewById<ImageView>(R.id.product_image)

        override fun bind(item: Any?) {
            if(item == null) {
                //TODO("заглушка")
            } else {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if(item is String) {
                ImageFun().setImage(item, imageProduct)
            }
        }
    }

    override fun createViewHolder(itemView: View): ImageViewHolder {
        return ImageViewHolder(itemView)
    }
}