package com.example.calculator

data class CalState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalOperation? = null
)
