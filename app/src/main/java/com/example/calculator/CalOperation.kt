package com.example.calculator

sealed class CalOperation(val symbol: String) {
    object Add: CalOperation("+")
    object Subtract: CalOperation("-")
    object Multiply: CalOperation("x")
    object Divide: CalOperation("/")
}