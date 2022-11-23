package com.lis.testapp.presentation.adapters

import android.view.View
import androidx.core.view.isVisible
import com.lis.testapp.databinding.FilterOptionBinding

class FilterViewHolder(private val binding: FilterOptionBinding) {

    init {
        binding.bind()
    }

    private fun FilterOptionBinding.bind() {
        closeButton.setOnClickListener { binding.root.isVisible = false }
        doneButton.setOnClickListener { binding.root.isVisible = false }
    }
}
