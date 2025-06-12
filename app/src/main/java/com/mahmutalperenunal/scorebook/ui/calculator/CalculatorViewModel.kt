package com.mahmutalperenunal.scorebook.ui.calculator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun calculate(expression: String) {
        try {
            _error.value = null
            val result = evaluateExpression(expression)
            _result.value = result.toString()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    private fun evaluateExpression(expression: String): Double {
        return 0.0
    }
} 