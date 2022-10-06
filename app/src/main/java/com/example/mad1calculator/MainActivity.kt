package com.example.mad1calculator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import kotlin.math.pow

class MainActivity : AppCompatActivity()
{
    private lateinit var textViewFirstNum: TextView
    private lateinit var textViewSign: TextView
    private lateinit var textViewSecondNum: TextView
    private lateinit var textViewResult: TextView

    private var stage: Int = 0
    private var firstNum: Double = 0.0
    private var secondNum: Double = 0.0
    private var resultNum: Double = 0.0
    private var signNum: Int = 0
    private var signAfterDot: Int = 0
    private var firstNumSign: Boolean = true
    private var secondNumSign: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewFirstNum = findViewById(R.id.textViewFirstNum)
        textViewSign = findViewById(R.id.textViewSign)
        textViewSecondNum = findViewById(R.id.textViewSecondNum)
        textViewResult = findViewById(R.id.textViewResult)

        textViewFirstNum.text = firstNum.toString()

        findViewById<Button>(R.id.buttonZero).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonOne).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonTwo).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonThree).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonFour).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonFive).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonSix).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonSeven).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonEight).setOnClickListener { buttonNumClick(it) }
        findViewById<Button>(R.id.buttonNine).setOnClickListener { buttonNumClick(it) }

        findViewById<Button>(R.id.buttonDot).setOnClickListener { dotClick() }

        findViewById<Button>(R.id.buttonChangeSign).setOnClickListener { changeSignClick() }

        findViewById<Button>(R.id.buttonPlus).setOnClickListener { signClick(it) }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { signClick(it) }
        findViewById<Button>(R.id.buttonMul).setOnClickListener { signClick(it) }
        findViewById<Button>(R.id.buttonDiv).setOnClickListener { signClick(it) }

        findViewById<Button>(R.id.buttonEqually).setOnClickListener { equallyClick() }

        findViewById<Button>(R.id.buttonDelAll).setOnClickListener { delAllClick() }
        findViewById<Button>(R.id.buttonDel).setOnClickListener { delClick() }
    }

    private fun buttonNumClick(view: View)
    {
        if (stage == 5)
        {
            stage = 0
            signAfterDot = 0
            firstNum = 0.0
            secondNum = 0.0
            signNum = 0
            textViewFirstNum.text = ""
            textViewSign.text = ""
            textViewSecondNum.text = ""
            firstNumSign = true
            secondNumSign = true
        }
        if (stage == 2)
        {
            stage++
        }

        val tempView: Button = view as Button
        val tempButtonNum = tempView.text.toString().toDouble()
        if (stage == 0)
        {
            signAfterDot = 0
            firstNum = if (firstNumSign)
            {
                firstNum * 10 + tempButtonNum
            }
            else
            {
                firstNum * 10 - tempButtonNum
            }
            textViewFirstNum.text = firstNum.toString()
            textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
        }
        if (stage == 1)
        {
            signAfterDot++
            firstNum = if (firstNumSign)
            {
                firstNum + (tempButtonNum / 10.0.pow(signAfterDot))
            }
            else
            {
                firstNum - (tempButtonNum / 10.0.pow(signAfterDot))
            }
            firstNum = firstNum.toBigDecimal().setScale(signAfterDot, RoundingMode.UP).toDouble()
            textViewFirstNum.text = firstNum.toString()
            textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
        }
        if (stage == 3)
        {
            signAfterDot = 0
            secondNum = if (secondNumSign)
            {
                secondNum * 10 + tempButtonNum
            }
            else
            {
                secondNum * 10 - tempButtonNum
            }
            textViewSecondNum.text = secondNum.toString()
            checkResult()
        }
        if (stage == 4)
        {
            signAfterDot++
            secondNum = if (secondNumSign)
            {
                secondNum + (tempButtonNum / 10.0.pow(signAfterDot))
            }
            else
            {
                secondNum - (tempButtonNum / 10.0.pow(signAfterDot))
            }
            secondNum = secondNum.toBigDecimal().setScale(signAfterDot, RoundingMode.UP).toDouble()
            textViewSecondNum.text = secondNum.toString()
            checkResult()
        }
    }

    private fun dotClick()
    {
        if (stage == 0 || stage == 3)
        {
            stage++
        }
    }

    private fun signClick(view: View)
    {
        val tempView: Button = view as Button
        val tempButtonSign = tempView.text.toString()
        if (stage == 3 || stage == 4)
        {
            stage = 2
            firstNum = resultNum
            textViewFirstNum.text = firstNum.toString()
            textViewSecondNum.text = ""
            textViewResult.text = ""
            secondNum = 0.0
            signNum = 0
            resultNum = 0.0
            firstNumSign = firstNum >= 0
            secondNumSign = true
            textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
        }
        if (tempButtonSign == "+")
        {
            signNum = 1
            textViewSign.text = "+"
            stage = 2
        }
        if (tempButtonSign == "—")
        {
            signNum = 2
            textViewSign.text = "—"
            stage = 2
        }
        if (tempButtonSign == "×")
        {
            signNum = 3
            textViewSign.text = "×"
            stage = 2
        }
        if (tempButtonSign == "÷")
        {
            signNum = 4
            textViewSign.text = "÷"
            stage = 2
        }
    }

    private fun changeSignClick()
    {
        if (stage == 5)
        {
            stage = 0
            firstNum = -0.0
            secondNum = 0.0
            signNum = 0
            textViewFirstNum.text = ""
            textViewSign.text = ""
            textViewSecondNum.text = ""
        }
        if (stage == 0 || stage == 1)
        {
            firstNum *= -1
            textViewFirstNum.text = firstNum.toString()
            textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
            firstNumSign = !firstNumSign
        }
        if (stage == 2 || stage == 3 || stage == 4)
        {
            secondNum *= -1
            textViewSecondNum.text = secondNum.toString()
            secondNumSign = !secondNumSign
            checkResult()
        }
    }

    private fun equallyClick()
    {
        if (stage == 3 || stage == 4)
        {
            stage = 5
            signAfterDot = 0
            firstNum = resultNum
            textViewFirstNum.text = firstNum.toString()
            textViewSign.text = ""
            textViewSecondNum.text = ""
            textViewResult.text = ""
            secondNum = 0.0
            signNum = 0
            resultNum = 0.0
            textViewFirstNum.setTextColor(Color.parseColor("#9370DB"))
            firstNumSign = firstNum >= 0
            secondNumSign = true
        }
    }

    private fun delAllClick()
    {
        stage = 0
        signAfterDot = 0
        firstNum = 0.0
        secondNum = 0.0
        signNum = 0
        textViewFirstNum.text = firstNum.toString()
        textViewSign.text = ""
        textViewSecondNum.text = ""
        textViewResult.text = ""
        textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
        firstNumSign = true
        secondNumSign = true
    }

    private fun delClick()
    {
        if (stage == 0 || stage == 1)
        {
            val tempStringOfNum: String = firstNum.toString()
            val tempDegree: Int = tempStringOfNum.length - (tempStringOfNum.indexOf(".") + 1)
            val tempNum: Double = (firstNum * 10.0.pow(tempDegree)) % 10
            firstNum = (firstNum * 10.0.pow(tempDegree)) - tempNum
            firstNum /= 10.0.pow(tempDegree)
            if (tempDegree == 1 && tempNum == 0.0)
            {
                stage = 0
                val tempNum2: Double = firstNum % 10
                firstNum = (firstNum - tempNum2) / 10
                if (firstNum == 0.0)
                {
                    firstNumSign = true
                }
            }
            else
            {
                firstNum = (firstNum * 10.0.pow(tempDegree)) - tempNum
                firstNum /= 10.0.pow(tempDegree)
                firstNum = firstNum.toBigDecimal().setScale(tempDegree - 1, RoundingMode.UP).toDouble()
            }
            textViewFirstNum.text = firstNum.toString()
        }

        if (stage == 3 || stage == 4)
        {
            val tempStringOfNum: String = secondNum.toString()
            val tempDegree: Int = tempStringOfNum.length - (tempStringOfNum.indexOf(".") + 1)
            val tempNum: Double = (secondNum * 10.0.pow(tempDegree)) % 10
            secondNum = (secondNum * 10.0.pow(tempDegree)) - tempNum
            secondNum /= 10.0.pow(tempDegree)
            if (tempDegree == 1 && tempNum == 0.0)
            {
                stage = 3
                val tempNum2: Double = secondNum % 10
                secondNum = (secondNum - tempNum2) / 10
                if (secondNum == 0.0)
                {
                    secondNumSign = true
                    textViewSecondNum.text = ""
                    textViewResult.text = ""
                    stage = 2
                }
                else
                {
                    textViewSecondNum.text = secondNum.toString()
                    checkResult()
                }
            }
            else
            {
                secondNum = (secondNum * 10.0.pow(tempDegree)) - tempNum
                secondNum /= 10.0.pow(tempDegree)
                secondNum = secondNum.toBigDecimal().setScale(tempDegree - 1, RoundingMode.UP).toDouble()
                textViewSecondNum.text = secondNum.toString()
                checkResult()
            }
        }

        if (stage == 5)
        {
            textViewFirstNum.setTextColor(Color.parseColor("#FFFFFF"))
            stage = 1
            delClick()
        }
    }

    private fun checkResult()
    {
        if (signNum == 1)
        {
            resultNum = firstNum + secondNum
        }
        if (signNum == 2)
        {
            resultNum = firstNum - secondNum
        }
        if (signNum == 3)
        {
            resultNum = firstNum * secondNum
        }
        if (signNum == 4)
        {
            resultNum = firstNum / secondNum
        }
        textViewResult.text = resultNum.toString()
    }
}