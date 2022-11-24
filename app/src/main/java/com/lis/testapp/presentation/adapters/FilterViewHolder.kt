package com.lis.testapp.presentation.adapters

import android.util.Log
import androidx.core.view.isVisible
import com.google.android.material.slider.RangeSlider
import com.lis.domain.tools.WITHOUT_ZEROS
import com.lis.domain.tools.convertIntPriceToString
import com.lis.testapp.R
import com.lis.testapp.databinding.FilterOptionBinding
import java.math.RoundingMode

abstract class FilterViewHolder(private val binding: FilterOptionBinding) {

    init {
        binding.bind()
    }

    var brand = ""

    var minPrice = 0
    var maxPrice = 10000

    var minSize = 3.0
    var maxSize = 8.0

    private var _brand = brand
    private var _minPrice = minPrice
    private var _maxPrice = maxPrice
    private var _minSize = minSize
    private var _maxSize = maxSize

    abstract fun onCancelFilter()
    abstract fun onDoneFilter()

    private fun FilterOptionBinding.bind() {
        bindPrice()
        bindSize()


        setPriceText(0, 10000)
        setSizeText(3.0, 8.0)

        root.setOnClickListener {
            cancelChanges()
            onCancelFilter()
        }

        closeButton.setOnClickListener {
            cancelChanges()
            onCancelFilter()
        }

        doneButton.setOnClickListener {
            confirmChanges()


            onDoneFilter()
        }
    }

    private fun FilterOptionBinding.confirmChanges() {
        brand = _brand
        minPrice = _minPrice
        maxPrice = _maxPrice
        minSize = _minSize
        maxSize = _maxSize

        sliderVisibility(priceSlider, false)
        sliderVisibility(sizeSlider, false)
    }

    private fun FilterOptionBinding.cancelChanges() {
        _brand = brand
        _minPrice = minPrice
        _maxPrice = maxPrice
        _minSize = minSize
        _maxSize = maxSize

        priceSlider.values = listOf(_minPrice.toFloat(), _maxPrice.toFloat())
        sliderVisibility(priceSlider, false)

        sizeSlider.values = listOf(_minSize.toFloat(), _maxSize.toFloat())
        sliderVisibility(sizeSlider, false)
    }

    private fun FilterOptionBinding.bindPrice() {
        priceTextView.setOnClickListener {
            sliderVisibility(priceSlider, !priceSlider.isVisible)
        }
        priceSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            _minPrice = values[0].toInt()
            _maxPrice = values[1].toInt()

            setPriceText(_minPrice, _maxPrice)
        }
    }

    private fun FilterOptionBinding.bindSize() {
        sizeTextView.setOnClickListener {
            sliderVisibility(sizeSlider, !sizeSlider.isVisible)
        }

        sizeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values

            _minSize = roundDouble(values[0])
            _maxSize = roundDouble(values[1])

            setSizeText(_minSize, _maxSize)
        }
    }

    private fun FilterOptionBinding.setPriceText(minPrice: Int, maxPrice: Int) {
        priceTextView.text = root.resources.getString(
            R.string.price_label,
            minPrice.convertIntPriceToString(WITHOUT_ZEROS),
            maxPrice.convertIntPriceToString(WITHOUT_ZEROS)
        )
    }

    private fun FilterOptionBinding.setSizeText(minSize: Double, maxSize: Double) {
        Log.e("sizeMINMAX", "$minSize - $maxSize")
        sizeTextView.text = root.resources.getString(
            R.string.size_label,
            "$minSize",
            "$maxSize"
        )
    }

    private fun sliderVisibility(slider: RangeSlider, isVisible: Boolean) {
        slider.isVisible = isVisible
    }

    private fun roundDouble(value: Float): Double {
        return value.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }
}
