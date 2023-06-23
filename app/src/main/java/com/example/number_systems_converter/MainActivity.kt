package com.example.number_systems_converter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.number_systems_converter.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var before: String? = null
    private var after: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()

        binding.equal.setOnClickListener {
            val number = binding.numberBox.text.toString()
            if (binding.numberBox.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter number", Toast.LENGTH_SHORT).show()
            } else {
                convertNumbers(number)
            }
        }
    }
    private val items = listOf("Binary", "Decimal", "Octal", "Hexadecimal")
    private fun initSpinner() {
        binding.spinnerFrom.apply {
            setItems(items)
            setOnSpinnerItemSelectedListener<String> { _, _, _, newText ->
                before = newText
            }
        }
        binding.spinnerTo.apply {
            setItems(items)
            setOnSpinnerItemSelectedListener<String> { _, _, _, newText ->
                after = newText
            }
        }
    }
    private fun convertDecimalToBinary(decimalNumber: Long): String {
        return Integer.toBinaryString(decimalNumber.toInt()).toString()
    }
    private fun convertBinaryToDecimal(binaryNumber: Long): Long {
        return Integer.parseInt(binaryNumber.toString(),2).toLong()
    }
    private fun convertHexadecimalToBinary(HNumber: String): String {
        val decimal= convertHexadecimalToDecimal(HNumber)
        return convertDecimalToBinary(decimal)
    }
    private fun convertBinaryToHexadecimal(BinaryNumber: String): String {
        val decimal =convertBinaryToDecimal(BinaryNumber.toLong())
        return convertDecimalToHexadecimal(decimal)
    }
    private fun convertDecimalToHexadecimal(DecimalNumber: Long): String {
        return Integer.toHexString(DecimalNumber.toInt()).uppercase(Locale.ROOT)
    }
    private fun convertHexadecimalToDecimal(HNumber: String): Long {
        return Integer.parseInt(HNumber,16).toLong()
    }
    private fun convertDecimalToOctal(DecimalNumber: Long): Long {
        return Integer.toOctalString(DecimalNumber.toInt()).toLong()
    }
    private fun convertOctalToDecimal(octalNumber: Long): Long {
    return Integer.parseInt(octalNumber.toString(),8).toLong()
    }
    private fun convertOctalToBinary(octalNumber: Long): String {
        val decimalNumber = convertOctalToDecimal(octalNumber)
        return convertDecimalToBinary(decimalNumber)
    }
    private fun convertBinaryToOctal(binaryNumber: Long): Long {
        val decimalNumber = convertBinaryToDecimal(binaryNumber)
        return convertDecimalToOctal(decimalNumber)
    }
    private fun convertHexadecimalToOctal(HNumber: String): Long {
        val decimalNumber = convertHexadecimalToDecimal(HNumber)
        return convertDecimalToOctal(decimalNumber)
    }
    private fun convertOctalToHexadecimal(octalNumber: Long): String {
        val decimalNumber = convertOctalToDecimal(octalNumber)
        return convertDecimalToHexadecimal(decimalNumber)
    }
    // A function to ensure that the user input a valid value
    private fun checkInput(before: String?, n: String): Boolean {
        if (before == "Binary" && n.any { it != '0' && it != '1' }) {
            Toast.makeText(this, "This system only allows 0 and 1", Toast.LENGTH_SHORT).show()
            return true
        }
        if ((before == "Decimal" || before == "Octal") && n.any { it.isLetter() }) {
            Toast.makeText(this, "This system only allows numbers", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }
    private fun convertNumbers(number: String) {
        if (checkInput(before, number)) {
            binding.result.text = ""
        }
        else {
            if (binding.numberBox.text == null) {
                Toast.makeText(this@MainActivity, "Please enter number", Toast.LENGTH_SHORT).show()
            }
            if (before == null || after == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Please select remaining system",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (before == "Binary" && after == "Decimal") {
                val result = convertBinaryToDecimal(number.toLong())
                binding.result.text = result.toString()
            }
            if (before == "Decimal" && after == "Binary") {
                val result = convertDecimalToBinary(number.toLong())
                binding.result.text = result
            }
            if (before == "Hexadecimal" && after == "Binary") {
                val result = convertHexadecimalToBinary(number)
                binding.result.text = result
            }
            if (before == "Binary" && after == "Hexadecimal") {
                val result = convertBinaryToHexadecimal(number)
                binding.result.text = result
            }
            if (before == "Hexadecimal" && after == "Decimal") {
                val result = convertHexadecimalToDecimal(number)
                binding.result.text = result.toString()
            }
            if (before == "Decimal" && after == "Hexadecimal") {
                val result = convertDecimalToHexadecimal(number.toLong())
                binding.result.text = result
            }
            if (before == "Decimal" && after == "Octal") {
                val result = convertDecimalToOctal(number.toLong())
                binding.result.text = result.toString()
            }
            if (before == "Octal" && after == "Decimal") {
                val result = convertOctalToDecimal(number.toLong())
                binding.result.text = result.toString()
            }
            if (before == "Octal" && after == "Binary") {
                val result = convertOctalToBinary(number.toLong())
                binding.result.text = result
            }
            if (before == "Binary" && after == "Octal") {
                val result = convertBinaryToOctal(number.toLong())
                binding.result.text = result.toString()
            }
            if (before == "Hexadecimal" && after == "Octal") {
                val result = convertHexadecimalToOctal(number)
                binding.result.text = result.toString()
            }
            if (before == "Octal" && after == "Hexadecimal") {
                val result = convertOctalToHexadecimal(number.toLong())
                binding.result.text = result
            }
            if (before == after) {
                Toast.makeText(this,
                    "Both systems are the same, choose a different system!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}