package com.mahmutalperenunal.scorebook.ui.calculator

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.databinding.FragmentCalculatorBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalculatorViewModel by viewModels()

    private var expression = StringBuilder()
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButtons()
    }

    private fun setupButtons() {
        binding.tbCalculatorHeader.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9,
            binding.btnDot, binding.btnPlus, binding.btnMinus,
            binding.btnMultiply, binding.btnDivide, binding.btnPercent
        )

        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(button) }
        }

        binding.btnC.setOnClickListener { clearExpression() }
        binding.btnAc.setOnClickListener { clearAll() }
        binding.btnDelete.setOnClickListener { deleteLast() }
        binding.btnEquals.setOnClickListener { calculateResult() }
    }

    private fun onButtonClick(button: MaterialButton) {
        when (val text = button.text.toString()) {
            in "0".."9" -> {
                expression.append(text)
                lastNumeric = true
                updateExpression()
            }

            "." -> {
                if (lastNumeric && !lastDot) {
                    expression.append(text)
                    lastNumeric = false
                    lastDot = true
                    updateExpression()
                }
            }

            "+", "-", "*", "/" -> {
                if (lastNumeric && expression.isNotEmpty()) {
                    expression.append(" $text ")
                    lastNumeric = false
                    lastDot = false
                    updateExpression()
                }
            }

            "%" -> {
                if (lastNumeric && expression.isNotEmpty()) {
                    expression.append(" / 100")
                    updateExpression()
                }
            }
        }
    }

    private fun clearExpression() {
        expression.clear()
        updateExpression()
        binding.tvResult.text = ""
        lastNumeric = false
        lastDot = false
    }

    private fun clearAll() {
        clearExpression()
    }

    private fun deleteLast() {
        if (expression.isNotEmpty()) {
            val lastChar = expression.last()
            expression.deleteCharAt(expression.length - 1)

            if (lastChar == '.') lastDot = false
            if (lastChar.isDigit()) lastNumeric = true
            updateExpression()
        }
    }

    private fun calculateResult() {
        try {
            val expr = expression.toString()
            if (expr.isBlank()) return

            val result = eval(expr)
            binding.tvResult.text = DecimalFormat("#.#").format(result)
        } catch (e: Exception) {
            binding.tvResult.text = getString(R.string.error)
        }
    }

    private fun updateExpression() {
        binding.tvExpression.text = expression.toString()
    }

    private fun eval(expr: String): Double {
        val tokens = expr.split(" ")
        if (tokens.isEmpty()) return 0.0

        var result = tokens[0].toDouble()
        var index = 1

        while (index < tokens.size) {
            val operator = tokens[index]
            val nextNumber = tokens.getOrNull(index + 1)?.toDoubleOrNull() ?: break

            when (operator) {
                "+" -> result += nextNumber
                "-" -> result -= nextNumber
                "*" -> result *= nextNumber
                "/" -> result /= nextNumber
            }
            index += 2
        }
        return result
    }

    private fun performHapticFeedback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.root.performHapticFeedback(android.view.HapticFeedbackConstants.CONFIRM)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}