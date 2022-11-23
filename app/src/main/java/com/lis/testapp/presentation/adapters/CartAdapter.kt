package com.lis.testapp.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lis.domain.models.BasketModel
import com.lis.domain.tools.ImageFun
import com.lis.domain.tools.WITH_ZEROS
import com.lis.domain.tools.convertIntPriceToString
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter

class CartAdapter(
    cartList: List<BasketModel>,
    idLayout: Int
) : BaseAdapter<CartAdapter.CartViewHolder>(
    cartList,
    idLayout,
) {

    interface OnCountClickListener {
        fun addClick(id: Int, position: Int, count: Int)
        fun removeClick(id: Int, position: Int, count: Int)
    }

    lateinit var countClickListener: OnCountClickListener

    fun setOnCountClickListener(listener: OnCountClickListener) {
        this.countClickListener = listener
    }

    inner class CartViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val productImage = itemView.findViewById<ImageView>(R.id.product_image)
        private val productTitle = itemView.findViewById<TextView>(R.id.product_title)
        private val productPrice = itemView.findViewById<TextView>(R.id.product_price)
        private val addButton = itemView.findViewById<ImageView>(R.id.add_button)
        private val removeButton = itemView.findViewById<ImageView>(R.id.remove_button)
        private val productCount = itemView.findViewById<TextView>(R.id.product_count)
        private val deleteButton = itemView.findViewById<ImageView>(R.id.delete_button)

        private var item: BasketModel? = null
        private var count = 1


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
            if (item is BasketModel) {
                this.item = item
                ImageFun().setImage(item.images, productImage)
                productTitle.text = item.title
                productPrice.text = item.price.convertIntPriceToString(WITH_ZEROS)
                productCount.text = count.toString()

                deleteButton.setOnClickListener {
                    clickListener.onButtonOnItemClick(item.id)
                }
                addButton.setOnClickListener {
                    if(count != 10)count++
                    countClickListener.addClick(item.id, bindingAdapterPosition, count)
                    notifyItemChanged(bindingAdapterPosition,productCount)
                }
                removeButton.setOnClickListener {
                    if(count != 0) count--
                    countClickListener.removeClick(item.id, bindingAdapterPosition, count)
                    notifyItemChanged(bindingAdapterPosition,productCount)
                }
            }
        }
    }

    override fun createViewHolder(itemView: View): CartViewHolder {
        return CartViewHolder(itemView)
    }
}