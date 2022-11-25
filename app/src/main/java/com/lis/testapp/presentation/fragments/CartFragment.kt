package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.domain.models.BasketModel
import com.lis.domain.tools.WITH_US
import com.lis.domain.tools.convertIntPriceToString
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCartBinding
import com.lis.testapp.presentation.adapters.CartAdapter
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.viewModels.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel by viewModel<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized) {
            binding = FragmentCartBinding.inflate(inflater, container, false)
            binding.viewInfo()
            binding.bindSwipeRefresh()
        }
        return binding.root
    }

    private fun FragmentCartBinding.bindSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.getCartData()
            swipeRefresh.isRefreshing = false
        }
    }

    private var adapter: CartAdapter? = null



    private var basketList: ArrayList<BasketModel>? = null

    private fun FragmentCartBinding.viewInfo() {
        viewModel.cartData.observe(viewLifecycleOwner) { cartModel ->
            if (cartModel != null) {
                basketList= cartModel.basket.map { it.copy(count = 1) } as ArrayList<BasketModel>
                if(adapter == null){
                    val adapter = CartAdapter(basketList!!, R.layout.cart_item)
                    adapter.setOnItemClickListener(onCartItemClickListener)
                    adapter.setOnCountClickListener(onCountClickListener)
                    productList.adapter = adapter
                    productList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                }
                totalText.text = setTotalPrice()
                deliveryText.text = cartModel.delivery
            }
        }

        closeButton.setOnClickListener { closeButtonClick() }
    }

    private val onCartItemClickListener = object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {
        }

        override fun onButtonOnItemClick(id: Int?) {
            basketList?.removeIf{it.id == id}
            binding.totalText.text = setTotalPrice()
        }

    }

    private val onCountClickListener = object : CartAdapter.OnCountClickListener {
        override fun addClick(id: Int, position: Int, count: Int) {
            binding.totalText.text = setTotalPrice(position, count)
        }

        override fun removeClick(id: Int, position: Int, count: Int) {
            binding.totalText.text = setTotalPrice(position, count)
        }

    }

    private fun setTotalPrice(position: Int? = null, count: Int? = null): String {
        var total = 0
        if(position != null && count != null) {
            basketList?.get(position)?.count = count
        }
        basketList?.forEach {
            total += it.price * it.count
        }
        return total.convertIntPriceToString(WITH_US)
    }


    private fun closeButtonClick() {
        super.getActivity()?.onBackPressed()
    }
}
