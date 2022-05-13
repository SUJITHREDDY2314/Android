package com.example.a7minuteapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import com.example.a7minuteapp.databinding.ActivityBmiBinding
import java.util.*

class BmiActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    var resultBmi= 0f
    var tts:TextToSpeech?= null
    var metricVisible = true
    lateinit var binding : ActivityBmiBinding
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarBmi)
        val actionBar = supportActionBar
        tts= TextToSpeech(this,this)
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarBmi.setNavigationOnClickListener {
            dialogPopUp()
        }
        binding.bmiCalculate.setOnClickListener {
            calculateBmiCheck()
        }
        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding.MetricUnit.id -> makeMetricViewVisible()
                binding.UsUnit.id -> makeUSViewVisible()
            }
        }

    }

    private fun calculateBmiCheck() {
        if(metricVisible){
            var w = binding.bmiWeightText.text.toString().toFloat()
            var h = binding.bmiHeightText.text.toString().toFloat()/100
            calculateBmi(w,h)
        }
        else{
            var w = binding.usWeightText.text.toString().toFloat()*0.453592f
            var fts = binding.usHeightTextFts.text.toString().toFloat()
            var inches = binding.usHeightTextInches.text.toString().toFloat()
            var h = (((12*fts)+inches)*2.54f)/100
            calculateBmi(w,h)
        }
    }

    private fun makeUSViewVisible() {
        binding.ttilBmiWeightText.visibility = View.GONE
        binding.ttilBmiHeightText.visibility = View.GONE
        binding.llBmiAnswer.visibility = View.GONE

        binding.ttilUsHeightTextFts.visibility = View.VISIBLE
        binding.ttilUsHeightTextInches.visibility = View.VISIBLE
        binding.ttilUsWeightText.visibility = View.VISIBLE
        metricVisible = false
    }

    private fun makeMetricViewVisible() {


        binding.ttilUsHeightTextFts.visibility = View.GONE
        binding.ttilUsHeightTextInches.visibility = View.GONE
        binding.ttilUsWeightText.visibility = View.GONE
        binding.llBmiAnswer.visibility = View.GONE
        binding.ttilBmiWeightText.visibility = View.VISIBLE
        binding.ttilBmiHeightText.visibility = View.VISIBLE
        metricVisible = true
    }

    private fun calculateBmi(w:Float,h:Float){


        resultBmi = w/(h*h)
        binding.bmiResultNumber.text = resultBmi.toString()
        binding.llBmiAnswer.visibility = View.VISIBLE
        binding.bmiResultType.text = when{
             resultBmi < 18.5f -> "Underweight"
             resultBmi<25f -> "Normal weight"
             resultBmi<30f -> "Overweight"
             else -> "Obese"
        }
        binding.bmiResultFeedBack.text = when{
            resultBmi < 18.5f -> "You need to take more foodt"
            resultBmi < 25f -> "Congratulations, You are in a good shape!"
            resultBmi < 30f -> "You are not in good shape"
            else -> "Strictly,You should control your diet"
        }
        binding.llBmiAnswer.visibility = View.VISIBLE
        tts!!.speak(binding.bmiResultFeedBack.text.toString(),TextToSpeech.QUEUE_ADD,null,"")

    }

    private fun dialogPopUp() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_reconfirmation2)
        dialog.show()
    }

    fun customDialogNo(view: View) {
        dialog.dismiss()
    }
    fun customDialogYes(view: View) {
        finish()
        dialog.dismiss()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this,"Language is not available", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"Initialization not done", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {

        super.onDestroy()
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
    }
}