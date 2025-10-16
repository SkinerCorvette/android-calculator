package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.view.View

class CalculatorActivity : AppCompatActivity() {

    private lateinit var displayText: TextView
    private lateinit var operationText: TextView

    private var currentInput = "0"
    private var firstOperand = 0.0
    private var secondOperand = 0.0
    private var currentOperator = ""
    private var shouldResetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ДОБАВЛЯЕМ FULLSCREEN КОД ЗДЕСЬ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

        setContentView(R.layout.activity_calculator)

        if (savedInstanceState != null) {
            currentInput = savedInstanceState.getString("currentInput", "0")
            firstOperand = savedInstanceState.getDouble("firstOperand", 0.0)
            currentOperator = savedInstanceState.getString("currentOperator", "")
            shouldResetInput = savedInstanceState.getBoolean("shouldResetInput", false)
        }

        initializeViews()
        setupNumberButtons()
        setupOperatorButtons()
        setupConverterButton()
        updateDisplay()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentInput", currentInput)
        outState.putDouble("firstOperand", firstOperand)
        outState.putString("currentOperator", currentOperator)
        outState.putBoolean("shouldResetInput", shouldResetInput)
    }

    private fun initializeViews() {
        displayText = findViewById(R.id.displayText)
        operationText = findViewById(R.id.operationText)
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberButtonClick((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.buttonDecimal).setOnClickListener {
            if (!currentInput.contains(".")) {
                if (currentInput == "0" || shouldResetInput) {
                    currentInput = "0."
                } else {
                    currentInput += "."
                }
                shouldResetInput = false
                updateDisplay()
            }
        }
    }

    private fun setupOperatorButtons() {
        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            clearCalculator()
        }

        findViewById<Button>(R.id.buttonBackspace).setOnClickListener {
            if (currentInput.length > 1) {
                currentInput = currentInput.dropLast(1)
            } else {
                currentInput = "0"
            }
            updateDisplay()
        }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperator("×") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperator("/") }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            calculateResult()
        }
    }

    private fun setupConverterButton() {
        findViewById<Button>(R.id.buttonToConverter).setOnClickListener {
            val intent = Intent(this, ConverterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onNumberButtonClick(number: String) {
        if (shouldResetInput || currentInput == "0") {
            currentInput = number
            shouldResetInput = false
        } else {
            currentInput += number
        }
        updateDisplay()
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator.isNotEmpty()) {
                calculateResult()
            }
            firstOperand = currentInput.toDouble()
            currentOperator = operator
            shouldResetInput = true
            updateOperationDisplay()
        }
    }

    private fun calculateResult() {
        if (currentOperator.isNotEmpty() && !shouldResetInput) {
            secondOperand = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "×" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
                else -> firstOperand
            }

            currentInput = if (result.isNaN()) {
                "Ошибка"
            } else {
                if (result % 1 == 0.0) {
                    result.toInt().toString()
                } else {
                    result.toString()
                }
            }

            operationText.text = ""
            currentOperator = ""
            shouldResetInput = true
            updateDisplay()
        }
    }

    private fun clearCalculator() {
        currentInput = "0"
        firstOperand = 0.0
        secondOperand = 0.0
        currentOperator = ""
        shouldResetInput = false
        operationText.text = ""
        updateDisplay()
    }

    private fun updateDisplay() {
        displayText.text = currentInput
    }

    private fun updateOperationDisplay() {
        operationText.text = "$firstOperand $currentOperator"
    }
}