package com.lis.testapp.presentation.fragments

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.Dimension.PX
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.lis.testapp.presentation.adapters.ImageViewPagerAdapter
import com.lis.testapp.presentation.decorator.HorizontalMarginItemDecoration
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCurrentProductBinding
import com.lis.testapp.presentation.viewModels.ProductDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.ceil

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
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
            if (productInfo != null) {
                productTitle.text = productInfo.title
                setRating(productInfo.rating)

                val imageList = productInfo.images
                imageSwitcherViewPager.adapter =
                    ImageViewPagerAdapter(imageList, R.layout.image_card_item, R.id.product_image)
                setupCarousel()

            }
        }
    }

    private fun FragmentCurrentProductBinding.setupCarousel() {
        imageSwitcherViewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
            page.alpha = 0.25f + (1 - abs(position))
        }

        imageSwitcherViewPager.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        imageSwitcherViewPager.addItemDecoration(itemDecoration)
    }

    private fun FragmentCurrentProductBinding.setRating(rating: Double) {
        repeat(rating.toInt()) {
            ratingLayout.addView(createImageView(RatingStar.FULL_STAR))
        }
        if (rating.mod(1f) != 0.toDouble()) {
            ratingLayout.addView(createImageView(RatingStar.HALF_STAR))
        }
        if (5 - ceil(rating).toInt() != 0) {
            ratingLayout.addView(createImageView(RatingStar.EMPTY_STAR))

        }
    }

    private fun createImageView(star: RatingStar): ImageView {
        val imageView = ImageView(requireContext())
        imageView.layoutParams =
            ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        imageView.setColorFilter(requireContext().getColor(R.color.yellow))
        imageView.adjustViewBounds = true
        val icon = when (star) {
            RatingStar.FULL_STAR -> {
                R.drawable.icon_star
            }
            RatingStar.HALF_STAR -> {
                R.drawable.icon_star_half
            }
            RatingStar.EMPTY_STAR -> {
                R.drawable.icon_star_border
            }
        }
        ImageFun().setImage(icon, imageView)
        return imageView
    }

    companion object {
        enum class RatingStar {
            FULL_STAR, HALF_STAR, EMPTY_STAR
        }
    }
}


