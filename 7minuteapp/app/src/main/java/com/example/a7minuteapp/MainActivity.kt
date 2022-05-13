package com.example.a7minuteapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val face = Typeface.createFromAsset(getAssets(), "Rockwell.otf");
        binding.startText.setTypeface(face)
        binding.bmiButton.setOnClickListener{
            goToBmiScreen()
        }
        binding.historyButton.setOnClickListener{
            goToHistoryScreen()
        }



    }

    private fun goToHistoryScreen() {
        Toast.makeText(this,"History",Toast.LENGTH_SHORT)
        val intent = Intent(this,HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun goToBmiScreen() {
        Toast.makeText(this@MainActivity,"Bmi",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,BmiActivity::class.java)
        startActivity(intent)
    }

    fun sujith(view: View) {
        Toast.makeText(this@MainActivity,"clicked",Toast.LENGTH_LONG).show()
        val intent = Intent(this,ExActivity::class.java)
        startActivity(intent)

    }

}