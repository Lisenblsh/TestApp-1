package com.lis.testapp.presentation.adapters

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.lis.domain.models.HomeStoreModel
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter


class HotSalesViewPagerAdapter(hotSalesList: List<HomeStoreModel>, idLayout: Int) :
    BaseAdapter<HotSalesViewPagerAdapter.HotSalesViewHolder>(hotSalesList, idLayout){
        inner class HotSalesViewHolder(itemView: View): BaseViewHolder(itemView) {

            private val image = itemView.findViewById<ImageView>(R.id.image_hot_sales)
            private val newIcon = itemView.findViewById<ImageView>(R.id.label_new_circle_hot_sales)
            private val title = itemView.findViewById<TextView>(R.id.title_hot_sales)
            private val subtitle = itemView.findViewById<TextView>(R.id.subtitle_hot_sales)
            private val button = itemView.findViewById<Button>(R.id.buy_button_hot_sales)

            private var item: HomeStoreModel? = null

            init {
                itemView.setOnClickListener {
                    val id = item?.id
                    clickListener.onItemClick(id)
                }
            }

            override fun bind(item: Any?) {
                if(item != null) {
                    showData(item)
                }
            }

            override fun showData(item: Any) {
                if(item is HomeStoreModel) {
                    this.item = item

                    ImageFun().setImage(item.picture,image)
                    newIcon.isVisible = item.is_new
                    title.text = item.title
                    subtitle.text = item.subtitle

                    button.setOnClickListener { clickListener.onButtonOnItemClick(item.id) }

                }
            }

        }

    override fun createViewHolder(itemView: View): HotSalesViewHolder {
        return HotSalesViewHolder(itemView)
    }
}