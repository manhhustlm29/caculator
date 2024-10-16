package com.example.caculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var textResult: TextView

    private var state: Int = 1
    private var op: Int = 0
    private var op1: Double = 0.0
    private var op2: Double = 0.0
    private var hasDecimal: Boolean = false
    private var decimalPlace: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.textResult)

        val buttonIds = listOf(
            R.id.bs, R.id.ce, R.id.c,
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.badau,
            R.id.cong, R.id.tru, R.id.nhan,
            R.id.chia, R.id.bang, R.id.cham
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_0 -> addDigit(0)
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
            R.id.cong -> setOperation(1)
            R.id.tru -> setOperation(2)
            R.id.nhan -> setOperation(3)
            R.id.chia -> setOperation(4)
            R.id.badau -> toggleSign()
            R.id.bang -> calculate()
            R.id.c -> reset()
            R.id.ce -> clearEntry()
            R.id.bs -> backspace()
            R.id.cham ->addDecimalPoint()
        }
    }

    private fun addDigit(digit: Int) {
        if (state == 1) {
            if (hasDecimal) {
                decimalPlace++
                op1 += digit / Math.pow(10.0, decimalPlace.toDouble())
            } else {
                op1 = op1 * 10 + digit
            }
            textResult.text = op1.toString()
        } else {
            if (hasDecimal) {
                decimalPlace++
                op2 += digit / Math.pow(10.0, decimalPlace.toDouble())
            } else {
                op2 = op2 * 10 + digit
            }
            textResult.text = op2.toString()
        }
    }

    private fun addDecimalPoint() {
        if (!hasDecimal) {
            hasDecimal = true
            // textResult.text = "${textResult.text}." // Thêm dấu chấm vào màn hình
        }
    }

    private fun setOperation(operation: Int) {
        op = operation
        state = 2
        hasDecimal = false // Reset trạng thái thập phân khi chuyển sang nhập số thứ 2
        decimalPlace = 0
    }

    private fun toggleSign() {
        if (state == 1) {
            op1 = -op1
            textResult.text = op1.toString()
        } else {
            op2 = -op2
            textResult.text = op2.toString()
        }
    }

    private fun calculate() {
        val result = when (op) {
            1 -> op1 + op2
            2 -> op1 - op2
            3 -> op1 * op2
            4 -> if (op2 != 0.0) op1 / op2 else Double.NaN
            else -> op1
        }

        textResult.text = if (result.isNaN()) {
            "Error" // Hiển thị thông báo lỗi khi chia cho 0
        } else {
            result.toString()
        }

        reset()
        op1 = result
    }

    private fun reset() {
        state = 1
        op1 = 0.0
        op2 = 0.0
        op = 0
        hasDecimal = false // Reset trạng thái thập phân
        decimalPlace = 0
    }

    private fun clearEntry() {
        if (state == 1) {
            op1 = 0.0
        } else {
            op2 = 0.0
        }
        textResult.text = "0"
        hasDecimal = false
        decimalPlace = 0
    }

    private fun backspace() {
        if (state == 1) {
            op1 = (op1 / 10).toInt().toDouble()
            textResult.text = op1.toString()
        } else {
            op2 = (op2 / 10).toInt().toDouble()
            textResult.text = op2.toString()
        }
    }
}


