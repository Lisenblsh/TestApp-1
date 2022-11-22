package com.lis.testapp.presentation.adapters.pagingAdapters

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.lis.domain.models.BasketModel
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.databinding.CartItemBinding
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.adapters.baseAdapters.BasePagingAdapter

class CartPagingAdapter(
    private val idLayout: Int
) : BasePagingAdapter<BasketModel, CartPagingAdapter.CartPagingViewHolder>(
    idLayout,
    CART_ITEMS_COMPARATOR
) {

    inner class CartPagingViewHolder(itemView: View) : BaseAdapter.BaseViewHolder(itemView) {

        private val productImage = itemView.findViewById<ImageView>(R.id.product_image)
        private val productTitle = itemView.findViewById<TextView>(R.id.product_title)
        private val productPrice = itemView.findViewById<TextView>(R.id.product_price)
        private val addButton = itemView.findViewById<ImageView>(R.id.add_button)
        private val removeButton = itemView.findViewById<ImageView>(R.id.remove_button)
        private val productCount = itemView.findViewById<TextView>(R.id.product_count)
        private val deleteButton = itemView.findViewById<ImageView>(R.id.delete_button)

        override fun bind(item: Any?) {
            if (item != null) {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if (item is BasketModel) {
                ImageFun().setImage(item.images, productImage)
                productTitle.text = item.title
                productPrice.text = convertIntPriceToString(item.price)
            }
        }
    }

    private fun convertIntPriceToString(price: Int): String {
        val _temp = price.div(1000)
        var stringPrice = ""
        if (_temp != 0) {
            stringPrice += "$$_temp,"
        }
        stringPrice += "${price.mod(1000)},00"
        return stringPrice
    }

    override fun createViewHolder(itemView: View): CartPagingViewHolder {
        return CartPagingViewHolder(itemView)
    }

    companion object {
        private val CART_ITEMS_COMPARATOR = object : ItemCallback<BasketModel>() {
            override fun areItemsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}