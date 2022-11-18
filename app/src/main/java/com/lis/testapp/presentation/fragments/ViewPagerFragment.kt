package com.lis.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentViewPagerBinding
import com.lis.testapp.domain.ViewPagerAdapter
import com.lis.testapp.domain.tools.ImageFun

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        binding.bindViewPager()
        return binding.root
    }

    private fun FragmentViewPagerBinding.bindViewPager() {
        val fragmentList = listOf(
            ExplorerFragment(),
            CartFragment(),
            FavouriteFragment(),
            AccountFragment()
        )

        val adapter = ViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabCustom = LayoutInflater.from(requireContext())
                .inflate(R.layout.tab_item, null) as LinearLayout
            val tabIcon = tabCustom.findViewById<ImageView>(R.id.tab_icon)
            val tabLabel = tabCustom.findViewById<TextView>(R.id.tab_label)
            val imageFun = ImageFun()
            tabLabel.visibility = View.GONE
            when (position) {
                0 -> {
                    tabIcon.layoutParams.height = tabIcon.layoutParams.height/2
                    tabLabel.text = resources.getString(R.string.label_explorer)
                    tabLabel.visibility = View.VISIBLE
                    imageFun.setImage(R.drawable.icon_explorer, tabIcon)
                }
                1 -> {
                    tabLabel.text = resources.getString(R.string.label_cart)
                    imageFun.setImage(R.drawable.icon_cart, tabIcon)
                }
                2 -> {
                    tabLabel.text = resources.getString(R.string.label_favourite)
                    imageFun.setImage(R.drawable.icon_favourite, tabIcon)
                }
                3 -> {
                    tabLabel.text = resources.getString(R.string.label_account)
                    imageFun.setImage(R.drawable.icon_account,tabIcon)
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
            if(tabIconHeight == null){
                tabIconHeight = tabCustom?.height
            }
            if (tabCustom != null) {
                val tabLabel = tabCustom.findViewById<TextView>(R.id.tab_label)
                val tabIcon = tabCustom.findViewById<ImageView>(R.id.tab_icon)
                if(visibility == View.VISIBLE){
                    tabIcon.layoutParams.height = tabIconHeight!!/2
                } else {
                    tabIcon.layoutParams.height = tabIconHeight!!

                }
                tabLabel.visibility = visibility
            }
        }

    }
}
