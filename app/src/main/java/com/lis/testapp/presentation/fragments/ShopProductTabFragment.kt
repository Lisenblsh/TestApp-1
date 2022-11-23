package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.lis.domain.models.CurrentProduct
import com.lis.domain.tools.WITH_ZEROS
import com.lis.domain.tools.convertIntPriceToString
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentShopProductTabBinding
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.adapters.selectorAdpter.CapacitySelectorAdapter
import com.lis.testapp.presentation.adapters.selectorAdpter.ColorSelectorAdapter

class ShopProductTabFragment() : Fragment() {

    private lateinit var binding: FragmentShopProductTabBinding
    private var productInfo: CurrentProduct? = null

    constructor(productInfo: CurrentProduct) : this() {
        this.productInfo = productInfo
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopProductTabBinding.inflate(inflater, container, false)
        binding.viewProductInfo()
        return binding.root
    }

    private fun FragmentShopProductTabBinding.viewProductInfo() {
        productInfo?.also { product ->
            cpuTextView.text = product.CPU
            cameraTextView.text = product.camera
            ssdTextView.text = product.ssd
            cdTextView.text = product.sd
            addToCartButton.text = resources.getString(
                R.string.add_to_cart,
                product.price.convertIntPriceToString(WITH_ZEROS)
            )
            setColorList(product)
            setCapacityList(product)

        }
    }

    private val onItemClickListener = object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {}

        override fun onButtonOnItemClick(id: Int?) {}
    }

    private fun FragmentShopProductTabBinding.setCapacityList(product: CurrentProduct) {
        this.capacityList.also { recyclerView ->
            val adapter = CapacitySelectorAdapter(product.capacity, R.layout.capacity_item).also {
                it.setOnItemClickListener(onItemClickListener)
            }
            recyclerView.adapter = adapter
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun FragmentShopProductTabBinding.setColorList(product: CurrentProduct) {
        this.colorList.also { recyclerView ->
            val adapter = ColorSelectorAdapter(product.color, R.layout.color_item).also {
                it.setOnItemClickListener(onItemClickListener)
            }
            recyclerView.adapter = adapter
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
