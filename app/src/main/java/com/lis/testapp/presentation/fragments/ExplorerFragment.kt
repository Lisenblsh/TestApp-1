package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.lis.domain.models.CategoryModel
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentExplorerBinding
import com.lis.testapp.databinding.FragmentViewPagerBinding
import com.lis.testapp.presentation.adapters.HotSalesViewPagerAdapter
import com.lis.testapp.presentation.adapters.baseAdapters.BaseAdapter
import com.lis.testapp.presentation.adapters.selectorAdpter.CategorySelectorAdapter
import com.lis.testapp.presentation.viewModels.ExplorerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExplorerFragment : Fragment() {

    private lateinit var binding: FragmentExplorerBinding

    private val viewModel by viewModel<ExplorerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExplorerBinding.inflate(inflater, container, false)
        binding.qrCodeButton.setOnClickListener {
            val directions =
                ViewPagerFragmentDirections.actionViewPagerFragmentToCurrentProductFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
        binding.viewCategory()
        binding.viewHotSales()
        return binding.root
    }



    private fun FragmentExplorerBinding.viewCategory() {
        viewModel.category.observe(viewLifecycleOwner) {
            if(it != null) {
                val adapter= CategorySelectorAdapter(
                    categoryList = it,
                    idLayout = R.layout.category_item
                )

                adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int?) {
                        if (position != null) {

                        }
                    }
                })
                categoryList.adapter = adapter
                (categoryList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                categoryList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun FragmentExplorerBinding.viewHotSales() {
        viewModel.hotSales.observe(viewLifecycleOwner) {
            if(it != null) {
                hotSalesViewPager.adapter = HotSalesViewPagerAdapter(it, R.layout.hot_sales_item)
            }
        }
    }
}
