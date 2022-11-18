package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentExplorerBinding
import com.lis.testapp.databinding.FragmentViewPagerBinding

class ExplorerFragment : Fragment() {

    private lateinit var binding: FragmentExplorerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }
}