package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCurrentProductBinding
import com.lis.testapp.presentation.viewModels.ProductDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentProductFragment : Fragment() {

    private lateinit var binding: FragmentCurrentProductBinding

    private val viewModel by viewModel<ProductDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentProductBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel.getProductInfo()
        }
        binding.viewProductInfo()
        return binding.root
    }

    private fun FragmentCurrentProductBinding.viewProductInfo() {
        viewModel.productInfo?.observe(viewLifecycleOwner) { productInfo ->
            if (productInfo != null) {
                productTitle.text = productInfo.title
                repeat(productInfo.rating.toInt()) {
                    val imageView = ImageView(requireContext())
                    imageView.setColorFilter(R.color.yellow)
                    ImageFun().setImage(R.drawable.icon_star, imageView)
                    ratingLayout.addView(imageView)
                }
            }
        }
    }
}
