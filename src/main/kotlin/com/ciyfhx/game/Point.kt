package com.ciyfhx.game

data class Point(
    val x: Int,
    val y: Int
)
infix fun Int.by(y: Int) = Point(this, y)