package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lis.domain.HttpException
import com.lis.domain.tools.ImageFun
import com.lis.testapp.R
import com.lis.testapp.databinding.FilterOptionBinding
import com.lis.testapp.databinding.FragmentExplorerBinding
import com.lis.testapp.databinding.FragmentViewPagerBinding
import com.lis.testapp.presentation.adapters.FilterViewHolder
import com.lis.testapp.presentation.adapters.FragmentViewPagerAdapter
import com.lis.testapp.presentation.viewModels.CartViewModel
import com.lis.testapp.presentation.viewModels.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    private val viewModel by viewModel<CartViewModel>()

    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized) {
            binding = FragmentViewPagerBinding.inflate(inflater, container, false)
            bind()
            onBackPressed()
        }
        return binding.root
    }

    private fun FragmentViewPagerBinding.bindFilterMenu() {
        val filterOptionBinding =
            FilterOptionBinding.inflate(LayoutInflater.from(requireContext()), binding.root, false)
        object :FilterViewHolder(filterOptionBinding){
            override fun onCancelFilter() {
                sharedViewModel.isFilterShow.value = false
            }

            override fun onDoneFilter() {
                sharedViewModel.isFilterShow.value = false
                sharedViewModel.brand = brand
                sharedViewModel.minPrice = minPrice
                sharedViewModel.maxPrice = maxPrice

                sharedViewModel.minSize = minSize
                sharedViewModel.maxSize = maxSize

                sharedViewModel.filterDone.value = true
            }
        }
        binding.constraint.addView(filterOptionBinding.root)

        sharedViewModel.isFilterShow.observe(viewLifecycleOwner) {
            Log.e("viewModelS", it.toString())
            filterOptionBinding.root.isVisible = it
        }
    }

    private fun bind() {
        try {
            binding.bindFilterMenu()
            binding.bindViewPager()
        } catch (e: HttpException) {
            showToast("Error: ${e.errorMessage}")
        } catch (e: Exception) {
            showToast("Error: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }

    private fun FragmentViewPagerBinding.bindViewPager() {
        val fragmentList = listOf(
            ExplorerFragment(),
            CartFragment(),
            FavouriteFragment(),
            AccountFragment()
        )

        val adapter = FragmentViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        viewPager.isSaveEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabCustom = LayoutInflater.from(requireContext())
                .inflate(R.layout.tab_item, null) as ConstraintLayout
            val tabIcon = tabCustom.findViewById<ImageView>(R.id.tab_icon)
            val tabLabel = tabCustom.findViewById<TextView>(R.id.tab_label)
            val countCircle = tabCustom.findViewById<ImageView>(R.id.count_circle)
            val countLabel = tabCustom.findViewById<TextView>(R.id.count_label)
            val imageFun = ImageFun()
            tabLabel.visibility = View.GONE
            when (position) {
                0 -> {
                    tabIcon.layoutParams.height = tabIcon.layoutParams.height / 2
                    tabLabel.text = resources.getString(R.string.label_explorer)
                    tabLabel.visibility = View.VISIBLE
                    imageFun.setImage(R.drawable.icon_explorer, tabIcon)
                }
                1 -> {
                    tabLabel.text = resources.getString(R.string.label_cart)
                    imageFun.setImage(R.drawable.icon_cart, tabIcon)
                    countCircle.isVisible= true
                    viewModel.cartData.observe(viewLifecycleOwner) {
                        if (it != null){
                            countLabel.text = it.basket.count().toString()
                        }
                    }

                }
                2 -> {
                    tabLabel.text = resources.getString(R.string.label_favourite)
                    imageFun.setImage(R.drawable.icon_favourite, tabIcon)
                }
                3 -> {
                    tabLabel.text = resources.getString(R.string.label_account)
                    imageFun.setImage(R.drawable.icon_account, tabIcon)
                }
            }
            tab.customView = tabCustom
        }.attach()


        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
    }

    private var tabIconHeight: Int? = null

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                setVisibility(View.VISIBLE, tab)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                setVisibility(View.GONE, tab)
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                setVisibility(View.VISIBLE, tab)
            }
        }

        private fun setVisibility(visibility: Int, tab: TabLayout.Tab) {
            val tabCustom = tab.customView
            if (tabIconHeight == null) {
                tabIconHeight = tabCustom?.height
            }
            if (tabCustom != null) {
                val tabLabel = tabCustom.findViewById<TextView>(R.id.tab_label)
                tabLabel.visibility = visibility
            }
        }

    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                if (binding.viewPager.currentItem == 0) {
                    super.getActivity()?.onBackPressed()

                } else {
                    binding.viewPager.currentItem = 0
                }
            }

    }
}
