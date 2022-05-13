package com.example.calculator

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric:Boolean = false
    var lastDot:Boolean = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        }


    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric=true

    }

    fun onClear(view: View) {
        binding.tvInput.text=""
        lastDot= false
        lastNumeric=false
    }

    fun onDecimal(view: View) {
        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastDot= true
        }
        else{
            Toast.makeText(this,"Enter numbers after decimal",Toast.LENGTH_LONG).show()
        }
    }
    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric= false
            lastDot= false
        }
        else{
            Toast.makeText(this,"Enter number first",Toast.LENGTH_LONG).show()
        }
    }
    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("+")||value.contains("-")||value.contains("*")||value.contains("+")
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = binding.tvInput.text.toString()
            var prefix =""

            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    //val s=(one.toDouble()-two.toDouble()).toString()
                    //binding.tvInput.text=String.format("%.2f", s)
                    var s = (one.toDouble()-two.toDouble())
                    var suj = if(s==s.toInt().toDouble()) s.toInt() else String.format("%.2f",s )
                    binding.tvInput.text= suj.toString()
                }
                else if(tvValue.contains("+")){
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    var s = (one.toDouble()+two.toDouble())
                    var suj = if(s==s.toInt().toDouble()) s.toInt() else String.format("%.2f",s )
                    binding.tvInput.text= suj.toString()
                }

                else if(tvValue.contains("/")){
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    var s = (one.toDouble()/two.toDouble())
                    var suj = if(s==s.toInt().toDouble()) s.toInt() else String.format("%.2f",s )
                    binding.tvInput.text= suj.toString()
                }
                else if(tvValue.contains("*")){
                    var splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var s = (one.toDouble()*two.toDouble())
                    var suj = if(s==s.toInt().toDouble()) s.toInt() else String.format("%.2f",s )
                    binding.tvInput.text= suj.toString()
                }

            }
            catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun onRemoveLast(view: View) {
        if(listOf<String>("+","-","/","*").contains(binding.tvInput.text.toString().takeLast(1))){
            lastNumeric = true

        }
        if(binding.tvInput.text.toString().takeLast(1)=="."){
            lastDot=false

        }
        binding.tvInput.text= binding.tvInput.text.toString().dropLast(1)


    }

}
