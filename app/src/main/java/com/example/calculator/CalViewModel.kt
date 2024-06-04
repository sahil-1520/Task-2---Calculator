package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalViewModel: ViewModel() {

    var state by mutableStateOf(CalState())
        private set

    fun onAction(action: CalActions) {
        when(action) {
            is CalActions.Number -> enterNumber(action.number)
            is CalActions.Decimal -> enterDecimal()
            is CalActions.Clear -> state = CalState()
            is CalActions.Delete -> performDeletion()
            is CalActions.Operation -> enterOperation(action.operation)
            is CalActions.Calculate -> performCalculation()
        }
    }

    //accepts the operation (+, -, *, /)
    private fun enterOperation(operation: CalOperation) {
        if(state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    //performs deletion of the end number / operation symbol
    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    //adds a decimal place whenever decimal button is pressed
    private fun enterDecimal() {
        if(state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if(!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(
                number2 = state.number2 + "."
            )
        }
    }

    //accepts the numbers
    private fun enterNumber(number: Int) {
        if(state.operation == null) {
            if(state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if(state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    //logic for calculation for all operations
    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if(number1 != null && number2 != null) {
            val result = when (state.operation) {
                is CalOperation.Add -> number1 + number2
                is CalOperation.Subtract -> number1 - number2
                is CalOperation.Multiply -> number1 * number2
                is CalOperation.Divide -> number1 / number2
                null -> return
            }
            var finalResult = result.toString()
            if(result.toString().endsWith(".0")) {
                finalResult = result.toInt().toString()
            }

            state = state.copy(
                number1 = finalResult.take(15),
                number2 = "",
                operation = null
            )
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}

