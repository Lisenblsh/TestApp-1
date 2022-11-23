package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCartBinding
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.adapters.pagingAdapters.CartAdapter
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
        }
        return binding.root
    }

    private var adapter: CartAdapter? = null

    private fun FragmentCartBinding.viewInfo() {
        viewModel.cartData.observe(viewLifecycleOwner) {
            if (it != null) {
                if(adapter == null){
                    val adapter = CartAdapter(it.basket, R.layout.cart_item)
                    adapter.setOnItemClickListener(onCartItemClickListener)
                    adapter.setOnCountClickListener(onCountClickListener)
                    productList.adapter = adapter
                    productList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                }
            }
        }

        closeButton.setOnClickListener { closeButtonClick() }
    }

    private val onCartItemClickListener = object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {
        }

        override fun onButtonOnItemClick(id: Int?) {

        }

    }

    private val onCountClickListener = object : CartAdapter.OnCountClickListener {
        override fun addClick(id: Int, position: Int, count: Int) {
        }

        override fun removeClick(id: Int, position: Int, count: Int) {
        }

    }


    private fun closeButtonClick() {
        super.getActivity()?.onBackPressed()
    }
}
