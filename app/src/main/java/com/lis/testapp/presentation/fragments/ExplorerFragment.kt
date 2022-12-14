package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.lis.testapp.R
import com.lis.testapp.databinding.FilterOptionBinding
import com.lis.testapp.databinding.FragmentExplorerBinding
import com.lis.testapp.presentation.adapters.BestSellerAdapter
import com.lis.testapp.presentation.adapters.FilterViewHolder
import com.lis.testapp.presentation.adapters.HotSalesViewPagerAdapter
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.adapters.selectorAdpter.CategorySelectorAdapter
import com.lis.testapp.presentation.viewModels.ExplorerViewModel
import com.lis.testapp.presentation.viewModels.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExplorerFragment : Fragment() {
    private lateinit var binding: FragmentExplorerBinding

    private val viewModel by viewModel<ExplorerViewModel>()

    private val sharedViewModel by lazy { requireParentFragment().getViewModel<SharedViewModel>()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized) {
            binding = FragmentExplorerBinding.inflate(inflater, container, false)
            binding.viewCategory()
            binding.viewHotSales()
            binding.viewBestSeller()
            binding.bindFilterMenu()
            binding.bindSwipeRefresh()
            binding.bindSearchView()
        }
        return binding.root
    }

    private fun FragmentExplorerBinding.bindSearchView() {
        searchCard.setOnClickListener {
            searchView.isIconified = false
        }
    }

    private fun FragmentExplorerBinding.bindFilterMenu() {
        filterButton.setOnClickListener {
            sharedViewModel.isFilterShow.value = true
        }
    }

    private fun FragmentExplorerBinding.bindSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.getCategoryList()
            viewModel.getStoreData()
            swipeRefresh.isRefreshing = false
        }
    }

    private var categorySelectorAdapter: CategorySelectorAdapter? = null

    private fun FragmentExplorerBinding.viewCategory() {
        viewModel.category.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                if (categorySelectorAdapter == null) {
                    categorySelectorAdapter =
                        CategorySelectorAdapter(
                            categoryList = list,
                            idLayout = R.layout.category_item
                        ).also {
                            it.setOnItemClickListener(onCategoryClick)
                        }
                    categoryList.adapter = categorySelectorAdapter
                    (categoryList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                    categoryList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                }
            }
        }
    }

    private var hotSalesViewPagerAdapter: HotSalesViewPagerAdapter? = null

    private fun FragmentExplorerBinding.viewHotSales() {
        viewModel.hotSales.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                if (hotSalesViewPagerAdapter == null) {
                    hotSalesViewPagerAdapter =
                        HotSalesViewPagerAdapter(list, R.layout.hot_sales_item).also {
                            it.setOnItemClickListener(onOnHotSalesItemClick)
                        }
                    hotSalesViewPager.adapter = hotSalesViewPagerAdapter
                }
            }
        }
    }

    private var bestSellerAdapter: BestSellerAdapter? = null

    private fun FragmentExplorerBinding.viewBestSeller() {
        viewModel.bestSeller.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                if (bestSellerAdapter == null) {
                    bestSellerAdapter = BestSellerAdapter(list, R.layout.best_seller_item).also {
                        it.setOnItemClickListener(onBestSellerItemClick)
                    }
                    bestSellerList.adapter = bestSellerAdapter
                    bestSellerList.layoutManager =
                        GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                }
            }
        }
    }


    private val onCategoryClick = object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {}
        override fun onButtonOnItemClick(id: Int?) {}
    }

    private fun directionToCurrentProduct() {
        val directions =
            ViewPagerFragmentDirections.actionViewPagerFragmentToCurrentProductFragment()
        NavHostFragment.findNavController(this@ExplorerFragment)
            .navigate(directions)
    }

    private val onBestSellerItemClick = object :
        BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {
            directionToCurrentProduct()
        }

        override fun onButtonOnItemClick(id: Int?) {
            Toast.makeText(requireContext(), "Product favourite status change", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private val onOnHotSalesItemClick = object :
        BaseAdapter.OnItemClickListener {
        override fun onItemClick(id: Int?) {
            directionToCurrentProduct()
        }

        override fun onButtonOnItemClick(id: Int?) {
            Toast.makeText(requireContext(), "Product $id add to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
