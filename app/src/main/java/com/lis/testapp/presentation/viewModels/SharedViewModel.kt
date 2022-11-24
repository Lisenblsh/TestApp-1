package com.lis.testapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var isFilterShow = MutableLiveData<Boolean>(false)

    var brand = ""

    var minPrice = 0
    var maxPrice = 10000

    var minSize = 3.0
    var maxSize = 8.0

    var filterDone = MutableLiveData<Boolean>(false)
}