package com.lis.testapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lis.testapp.R
import com.lis.testapp.databinding.FragmentCurrentProductBinding

class CurrentProductFragment : Fragment() {

    private lateinit var binding: FragmentCurrentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

}