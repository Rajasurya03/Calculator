package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.time.Instant

//import kotlinx.android.synthetic.main.activity_main.*

//ID
private lateinit var tvOperation: TextView
private lateinit var tvResult: TextView

private var canAddOperator = false
private var canAddDecimal = true
private var canAddNumber = true

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvOperation=findViewById(R.id.tvOperation)
        tvResult=findViewById(R.id.tvResult)
        val menu=findViewById<Button>(R.id.bntMenu)
        menu.setOnClickListener {
            intent=Intent(this, ConverterActivity::class.java)
            startActivity(intent)
        }
    }
    fun numberAction(view: View){
        if(view is Button) {
            if(view.text=="."){
                if(canAddDecimal)
                    tvOperation.append(view.text)
                canAddDecimal=false
            }
            else {
                tvOperation.append(view.text)
                canAddDecimal=true
            }
        }
        tvResult.text=calculateResults()
        canAddOperator=true
    }
    fun operationAction(view: View){
        if(view is Button && (canAddOperator || (view.text=="-" && tvOperation.length()==0))){
            if(view.text=="-" && tvOperation.length()==0)
                tvOperation.append("0")
            tvOperation.append(view.text)
            canAddOperator=false
            canAddDecimal=false
            tvResult.text=calculateResults()
        }
    }
    fun allClearAction(view: View){
        tvOperation.text = ""
        tvResult.text = ""
        canAddOperator = false
        canAddDecimal = true
    }
    fun clearAction(view: View){
        tvOperation.text = ""
        canAddOperator = false
        canAddDecimal = true
    }
    fun backSpaceAction(view: View){
        if(tvOperation.length()>0){
            tvOperation.text= tvOperation.text.subSequence(0,tvOperation.length()-1)
        }
    }
    fun equalToAction(view: View){
        tvOperation.text = calculateResults()
        tvResult.text = ""
        canAddOperator = true
        canAddDecimal = false
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty())
            return ""
        val timeDivision=timeDivisionCalculate(digitsOperators)
            if (timeDivision.isEmpty())
                return ""
        val result=addSubtractCalculate(timeDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Any {
        var result = passedList[0] as Float
        for(i in passedList.indices){
            if (passedList[i] is Char && i !=passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit =passedList[i+1] as Float
                if(operator=='+')
                    result+=nextDigit
                if (operator=='-')
                    result-=nextDigit
            }
        }
        return result
    }

    private fun timeDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while(list.contains('x')||list.contains('÷')){
            list=calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for(i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex&& i<restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when(operator){
                    'x' ->{
                        newList.add(prevDigit*nextDigit)
                        restartIndex = i+1
                    }
                    '÷'->{
                        newList.add(prevDigit/ nextDigit)
                        restartIndex = i+1
                    }
                    else ->{
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if(i>restartIndex)
                newList.add(passedList[i])
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any>{
        val list= mutableListOf<Any>()
        var currentDigit = ""
        for(character in tvOperation.text){
            if (character.isDigit()||character=='.')
                currentDigit+=character
            else{
                list.add(currentDigit.toFloat())
                currentDigit=""
                list.add(character)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }
}