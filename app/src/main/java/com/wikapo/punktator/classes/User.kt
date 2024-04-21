package com.wikapo.punktator.classes

import androidx.compose.ui.graphics.Color

class User(
    val name: String,
    val color: Color = Color.Green,
    var points: Int = 0,
)