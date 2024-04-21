package com.wikapo.punktator.classes

import androidx.annotation.StringRes
import com.wikapo.punktator.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int
) {
    data object Selector :
            Screen("selector", R.string.screen_prepare)
}