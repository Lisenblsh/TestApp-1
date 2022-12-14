package com.lis.testapp.presentation.fragments

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.lis.domain.models.CurrentProduct
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCurrentProductBinding
import com.lis.testapp.presentation.adapters.FragmentViewPagerAdapter
import com.lis.testapp.presentation.adapters.ImageViewPagerAdapter
import com.lis.testapp.presentation.decorator.HorizontalMarginItemDecoration
import com.lis.testapp.presentation.viewModels.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.ceil

class CurrentProductFragment : Fragment() {

    private lateinit var binding: FragmentCurrentProductBinding

    private val viewModel by viewModel<ProductDetailsViewModel>()

    private var fragmentAdapter: FragmentViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCurrentProductBinding.inflate(inflater, container, false)
        binding.viewProductInfo()
        binding.bindSwipeRefresh()
        return binding.root
    }


    private fun FragmentCurrentProductBinding.bindSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.getProductInfo()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun FragmentCurrentProductBinding.viewProductInfo() {
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
            if (productInfo != null) {
                productTitle.text = productInfo.title
                val imageList = productInfo.images
                imageSwitcherViewPager.adapter =
                    ImageViewPagerAdapter(imageList, R.layout.image_card_item)
                setCarousel()
                setRating(productInfo.rating)
                setTab(productInfo)
            }
        }

        closeButton.setOnClickListener { closeButtonClick() }
        cartButton.setOnClickListener { cartButtonClick() }
    }

    private fun closeButtonClick() {
        super.getActivity()?.onBackPressed()
    }

    private fun cartButtonClick() {
        val directions =
            CurrentProductFragmentDirections.actionCurrentProductFragmentToCartFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }

    private fun FragmentCurrentProductBinding.setTab(productInfo: CurrentProduct) {
        viewPagerProduct.adapter = null
        val list = listOf(
            ShopProductTabFragment(productInfo),
            DetailsProductTabFragment(),
            FeaturesProductTabFragment()
        )

        fragmentAdapter = FragmentViewPagerAdapter(list, childFragmentManager, lifecycle)
        viewPagerProduct.adapter = fragmentAdapter

        TabLayoutMediator(tabLayoutProduct, viewPagerProduct) { tab, position ->
            val stringArray =
                requireContext().resources.getStringArray(R.array.tabs_layout_product)
            when (position) {
                0 -> tab.text = stringArray[position]
                1 -> tab.text = stringArray[position]
                2 -> tab.text = stringArray[position]
            }
        }.attach()
    }

    private fun FragmentCurrentProductBinding.setCarousel() {

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

        if (imageSwitcherViewPager.itemDecorationCount != 0) {
            imageSwitcherViewPager.removeItemDecorationAt(0)
        }

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        imageSwitcherViewPager.addItemDecoration(itemDecoration, 0)

    }

    private fun FragmentCurrentProductBinding.setRating(rating: Double) {
        ratingLayout.removeAllViews()
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


