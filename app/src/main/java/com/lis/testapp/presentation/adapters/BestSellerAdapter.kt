package com.lis.testapp.presentation.adapters

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lis.domain.models.BestSellerModel
import com.lis.domain.tools.ImageFun
import com.lis.domain.tools.WITHOUT_ZEROS
import com.lis.domain.tools.convertIntPriceToString
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter

class BestSellerAdapter(bestSellerList: List<BestSellerModel>, idLayout: Int) :
    BaseAdapter<BestSellerAdapter.BestSellerViewHolder>(bestSellerList, idLayout) {

    inner class BestSellerViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image_best_seller)
        private val fab =
            itemView.findViewById<FloatingActionButton>(R.id.add_favourite_button_best_seller)
        private val discountPrice =
            itemView.findViewById<TextView>(R.id.discount_price_text_best_seller)
        private val price = itemView.findViewById<TextView>(R.id.price_text_best_seller)
        private val title = itemView.findViewById<TextView>(R.id.title__best_seller)

        private var item: BestSellerModel? = null

        init {
            itemView.setOnClickListener {
                val id = item?.id
                clickListener.onItemClick(id)
            }
        }

        override fun bind(item: Any?) {
            if (item != null) {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if (item is BestSellerModel) {
                this.item = item

                val imageFun = ImageFun()

                imageFun.setImage(item.picture, image)
                discountPrice.text = item.price_without_discount.convertIntPriceToString(WITHOUT_ZEROS)
                price.text = item.discount_price.convertIntPriceToString(WITHOUT_ZEROS)
                price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                title.text = item.title

                val icon = if(item.is_favorites) {
                    R.drawable.icon_favourite_fill
                } else {
                    R.drawable.icon_favourite
                }
                imageFun.setImage(icon, (fab as ImageView))
                fab.setOnClickListener { clickListener.onButtonOnItemClick(item.id) }
            }
        }

    }

    override fun createViewHolder(itemView: View): BestSellerViewHolder {
        return BestSellerViewHolder(itemView)
    }
}