package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ConverterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
    }
    fun Converter (view: View){
        if(view is Button){
            val bntCurrency=findViewById<Button>(R.id.bntCurrency)
            bntCurrency.setOnClickListener {
                intent= Intent(this, CurrencyActivity::class.java)
            }
            startActivity(intent)
        }
    }
}