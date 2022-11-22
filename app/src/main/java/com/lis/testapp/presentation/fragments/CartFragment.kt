package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCartBinding
import com.lis.testapp.presentation.adapters.pagingAdapters.CartPagingAdapter
import com.lis.testapp.presentation.viewModels.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel by viewModel<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.viewInfo()
        return binding.root
    }


    private fun FragmentCartBinding.viewInfo() {
        val adapter = CartPagingAdapter(R.layout.cart_item)
        productList.adapter = adapter
        productList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.pagingStoreData.collectLatest(adapter::submitData)
        }

        closeButton.setOnClickListener{closeButtonClick()}
    }

    private fun closeButtonClick() {
        super.getActivity()?.onBackPressed()
    }
}
