package com.example.number_systems_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.number_systems_converter.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var before: String? = null
    var after: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()

        binding.equal.setOnClickListener {
            val number = binding.numberBox.text.toString()
            convertNumbers(number)
        }
    }
    //The arrow will be modified later due to time constraints
    val items1 = listOf("From                     v", "Binary", "Decimal", "Octal", "Hexadecimal")
    val items2 = listOf("To                           v", "Binary", "Decimal", "Octal", "Hexadecimal")

    private fun initSpinner() {
        val fAdapter = ArrayAdapter(this,com.google.android.material.R.layout.support_simple_spinner_dropdown_item,items1)

        val tAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, items2)

        binding.spinnerFrom.apply {
            adapter = fAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    before = items1[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {TODO("Not yet implemented")}
            }
        }
        binding.spinnerTo.apply {
            adapter = tAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    after = items2[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {TODO("Not yet implemented")}
            }
        }
    }
    private fun convertDecimalToBinary(decimalNumber: Int): Int {
        var dNumber = decimalNumber
        var binaryNumber = 0
        var remainder: Int
        var i = 1
        while (dNumber != 0) {
            remainder = dNumber % 2
            dNumber /= 2
            binaryNumber += (remainder * i)
            i *= 10
        }
        return binaryNumber
    }
    private fun convertBinaryToDecimal(binaryNumber: Int): Int {
        var bNumber = binaryNumber
        var decimalNo = 0
        var power = 0
        while (bNumber > 0) {
            val r = bNumber % 10
            decimalNo = (decimalNo + r * 2.0.pow(power.toDouble())).toInt()
            bNumber /= 10
            power++
        }
        return decimalNo
    }
    private fun convertHexadecimalToBinary(HexaNumber: String): String {
        val decimal = HexaNumber.toLong(16)
        return decimal.toString(2)
    }
    private fun convertBinaryToHexadecimal(BinaryNumber: String): String {
        val decimal = BinaryNumber.toInt(2)
        return decimal.toString(16).uppercase(Locale.getDefault())
    }
    private fun convertDecimalToHexadecimal(DecimalNumber: Int): String {
        val bNumber = convertDecimalToBinary(DecimalNumber)
        return convertBinaryToHexadecimal(bNumber.toString())
    }
    private fun convertHexadecimalToDecimal(HexaNumber: String): Int {
        val bNumber = convertHexadecimalToBinary(HexaNumber)
        return convertBinaryToDecimal(bNumber.toInt())
    }
    private fun convertDecimalToOctal(DecimalNumber: Int): Long {
        var dNumber = DecimalNumber
        var octalNumber = 0
        var i = 1

        while (dNumber != 0) {
            octalNumber += (dNumber % 8) * i
            dNumber /= 8
            i *= 10
        }
        return octalNumber.toLong()
    }
    private fun convertOctalToDecimal(octalNumber: Long): Int {
        var decimal = 0L
        var oNumber = octalNumber
        var i = 0
        var rem: Long

        while (oNumber != 0L) {
            rem = oNumber % 10
            decimal += rem * 8.0.pow(i.toDouble()).toLong()
            ++i
            oNumber /= 10
        }

        return decimal.toInt()
    }
    private fun convertOctalToBinary(octalNumber: Long): Int {
        val decimalNumber = convertOctalToDecimal(octalNumber)
        return convertDecimalToBinary(decimalNumber)
    }
    private fun convertBinaryToOctal(binaryNumber: Int):Long{
        val decimalNumber = convertBinaryToDecimal(binaryNumber)
        return convertDecimalToOctal(decimalNumber)
    }
    private fun convertHexadecimalToOctal(HexaNumber: String):Long{
        val decimalNumber = convertHexadecimalToDecimal(HexaNumber)
        return convertBinaryToOctal(decimalNumber)
    }
    private fun convertOctalToHexadecimal(octalNumber: Long):String{
        val decimalNumber = convertOctalToDecimal(octalNumber)
        return convertDecimalToHexadecimal(decimalNumber)
    }

    // A function to ensure that the user input a valid value
    private fun containLetters(n:String):Boolean{
        return n.any { it.isLetter() }
    }
    private fun containsSymbols(str: String): Boolean {
        val regex = Regex("[^A-Za-z0-9 ]")
        return regex.containsMatchIn(str)
    }
    // hexa(symbols) && binary(0,1)
    private fun convertNumbers(number: String) {
        if(binding.numberBox.text.toString().isEmpty()){
            Toast.makeText(this@MainActivity, "Please enter number", Toast.LENGTH_SHORT).show()
        }
        if((before == "Binary" || before == "Decimal" || before == "Octal") && (containLetters(number) || containsSymbols(number)) ){
            Toast.makeText(this, "This system only allows numbers",Toast.LENGTH_SHORT).show()
            binding.result.text = ""
        }
        else{
            if(binding.numberBox.text == null){
                Toast.makeText(this@MainActivity, "Please enter number", Toast.LENGTH_SHORT).show()
            }
            if (before == items1[0] || after == items2[0]) {
                Toast.makeText(this@MainActivity, "Please select remaining system", Toast.LENGTH_SHORT).show()
            }
            if (before == "Binary" && after == "Decimal") {
                val result = convertBinaryToDecimal(number.toInt())
                binding.result.text = result.toString()
            }
            if (before == "Decimal" && after == "Binary") {
                val result = convertDecimalToBinary(number.toInt())
                binding.result.text = result.toString()
            }
            if (before == "Hexadecimal" && after == "Binary") {
                val result = convertHexadecimalToBinary(number)
                binding.result.text = result
            }
            if (before == "Binary" && after =="Hexadecimal") {
                val result = convertBinaryToHexadecimal(number)
                binding.result.text = result
            }
            if(before == "Hexadecimal" && after == "Decimal"){
                val result = convertHexadecimalToDecimal(number)
                binding.result.text = result.toString()
            }
            if(before == "Decimal" && after == "Hexadecimal"){
                val result = convertDecimalToHexadecimal(number.toInt())
                binding.result.text = result
            }
            if(before == "Decimal" && after == "Octal"){
                val result = convertDecimalToOctal(number.toInt())
                binding.result.text = result.toString()
            }
            if(before == "Octal" && after == "Decimal"){
                val result = convertOctalToDecimal(number.toLong())
                binding.result.text = result.toString()
            }
            if(before == "Octal" && after == "Binary"){
                val result = convertOctalToBinary(number.toLong())
                binding.result.text = result.toString()
            }
            if(before == "Binary" && after == "Octal"){
                val result = convertBinaryToOctal(number.toInt())
                binding.result.text = result.toString()
            }
            if(before == "Hexadecimal" && after == "Octal"){
                val result = convertHexadecimalToOctal(number)
                binding.result.text = result.toString()
            }
            if(before == "Octal" && after == "Hexadecimal"){
                val result = convertOctalToHexadecimal(number.toLong())
                binding.result.text = result
            }
            if(before == after){
                Toast.makeText(this,"Both systems are the same, choose a different system!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
