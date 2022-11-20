package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lis.domain.models.CurrentProduct
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentShopProductTabBinding
import com.lis.testapp.presentation.adapters.CapacitySelectorViewHolder
import com.lis.testapp.presentation.adapters.ColorSelectorAdapter

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


            this.colorList.also {
                it.adapter = ColorSelectorAdapter(product.color, R.layout.color_item)
                it.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            }

            this.capacityList.also {
                it.adapter = CapacitySelectorViewHolder(product.capacity.reversed(), R.layout.capacity_item)
                it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            }

        }
    }
}
