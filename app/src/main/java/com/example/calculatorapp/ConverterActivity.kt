package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import android.os.Build
import android.view.View

class ConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        setContentView(R.layout.activity_converter)

        setupConvertButtons()
        setupCalculatorButton()
    }

    private fun setupConvertButtons() {
        // Градусы в радианы
        findViewById<Button>(R.id.convertDegreesButton).setOnClickListener {
            convertDegreesToRadians()
        }

        // Километры в мили
        findViewById<Button>(R.id.convertKmButton).setOnClickListener {
            convertKmToMiles()
        }

        // Метры в футы
        findViewById<Button>(R.id.convertMetersButton).setOnClickListener {
            convertMetersToFeet()
        }

        // Десятичные в шестнадцатеричные
        findViewById<Button>(R.id.convertHexButton).setOnClickListener {
            convertToHex()
        }

        // Десятичные в двоичные
        findViewById<Button>(R.id.convertBinaryButton).setOnClickListener {
            convertToBinary()
        }
    }

    private fun convertDegreesToRadians() {
        val degreesInput = findViewById<EditText>(R.id.degreesInput)
        val radiansOutput = findViewById<TextView>(R.id.radiansOutput)

        val degreesText = degreesInput.text.toString()
        if (degreesText.isNotEmpty()) {
            try {
                val degrees = degreesText.toDouble()
                val radians = degrees * PI / 180.0
                radiansOutput.text = String.format("%.6f", radians)
            } catch (e: NumberFormatException) {
                radiansOutput.text = "Ошибка"
            }
        } else {
            radiansOutput.text = "0"
        }
    }

    private fun convertKmToMiles() {
        val kmInput = findViewById<EditText>(R.id.kmInput)
        val milesOutput = findViewById<TextView>(R.id.milesOutput)

        val kmText = kmInput.text.toString()
        if (kmText.isNotEmpty()) {
            try {
                val km = kmText.toDouble()
                val miles = km * 0.62
                milesOutput.text = String.format("%.2f", miles)
            } catch (e: NumberFormatException) {
                milesOutput.text = "Ошибка"
            }
        } else {
            milesOutput.text = "0"
        }
    }

    private fun convertMetersToFeet() {
        val metersInput = findViewById<EditText>(R.id.metersInput)
        val feetOutput = findViewById<TextView>(R.id.feetOutput)

        val metersText = metersInput.text.toString()
        if (metersText.isNotEmpty()) {
            try {
                val meters = metersText.toDouble()
                val feet = meters * 3.28
                feetOutput.text = String.format("%.2f", feet)
            } catch (e: NumberFormatException) {
                feetOutput.text = "Ошибка"
            }
        } else {
            feetOutput.text = "0"
        }
    }

    private fun convertToHex() {
        val decimalInput = findViewById<EditText>(R.id.decimalHexInput)
        val hexOutput = findViewById<TextView>(R.id.hexOutput)

        val decimalText = decimalInput.text.toString()
        if (decimalText.isNotEmpty()) {
            try {
                val decimal = decimalText.toInt()
                val hex = Integer.toHexString(decimal).uppercase()
                hexOutput.text = hex
            } catch (e: NumberFormatException) {
                hexOutput.text = "Ошибка"
            }
        } else {
            hexOutput.text = "0"
        }
    }

    private fun convertToBinary() {
        val decimalInput = findViewById<EditText>(R.id.decimalBinaryInput)
        val binaryOutput = findViewById<TextView>(R.id.binaryOutput)

        val decimalText = decimalInput.text.toString()
        if (decimalText.isNotEmpty()) {
            try {
                val decimal = decimalText.toInt()
                val binary = Integer.toBinaryString(decimal)
                binaryOutput.text = binary
            } catch (e: NumberFormatException) {
                binaryOutput.text = "Ошибка"
            }
        } else {
            binaryOutput.text = "0"
        }
    }

    private fun setupCalculatorButton() {
        findViewById<Button>(R.id.buttonToCalculator).setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}