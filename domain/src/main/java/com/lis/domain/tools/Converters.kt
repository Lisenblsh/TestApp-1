package com.lis.domain.tools

import androidx.annotation.IntDef

fun Int.convertIntPriceToString(@ConvertMode mode: Int): String {
    val price = this.toString()
    var stringPrice = "$"
    var startIndex = 0
    val count = price.count()

    if(count >= 4) {
        stringPrice+= price.substring(0,count-3)+","
        startIndex = count-3
    }

    stringPrice += price.substring(startIndex)
    stringPrice += when(mode) {
        WITH_US -> " us"
        WITH_ZEROS -> ".00"
        WITHOUT_ZEROS -> ""
        else -> ""
    }
    return stringPrice
}

@IntDef(*[WITH_US,WITH_ZEROS,WITHOUT_ZEROS])
annotation class ConvertMode

const val WITH_US = 0
const val WITH_ZEROS = 1
const val WITHOUT_ZEROS = 3
