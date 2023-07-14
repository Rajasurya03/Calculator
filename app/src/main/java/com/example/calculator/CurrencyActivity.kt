package com.example.calculator

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private lateinit var tvCurrency1: TextView
private lateinit var tvCurrency2: TextView

private var canAddOperator = false
private var canAddDecimal = true
class CurrencyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        tvCurrency1=findViewById(R.id.tvCurrency1)
        tvCurrency2=findViewById(R.id.tvCurrency2)
    }
    fun numberAction(view: View){
        if(view is Button) {
            if(view.text=="."){
                if(tvCurrency1.length()==0)
                    tvCurrency1.append("0")
                if(canAddDecimal)
                    tvCurrency1.append(view.text)
                canAddDecimal=false
            }
            else {
                tvCurrency1.append(view.text)
            }
        }
        tvCurrency2.text=calculateResults()
        canAddOperator=true
    }

    private fun calculateResults(): CharSequence {
        val res: Float = (tvCurrency1.text).toString().toFloat() / 82
        return res.toString()
    }

    fun clearAction(view: View){
        tvCurrency1.text = ""
        canAddOperator = false
        canAddDecimal = true
    }
    fun backSpaceAction(view: View){
        if(tvCurrency1.length()>0){
            tvCurrency1.text= tvCurrency1.text.subSequence(0,tvCurrency1.length()-1)
        }
    }
}