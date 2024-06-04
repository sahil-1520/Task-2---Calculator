package com.example.calculator

sealed class CalActions {
    data class Number(val number: Int): CalActions()
    object Clear: CalActions()
    object Delete: CalActions()
    object Decimal: CalActions()
    object Calculate: CalActions()
    data class Operation(val operation: CalOperation): CalActions()
}