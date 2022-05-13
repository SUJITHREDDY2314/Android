package com.example.ageminutes

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ageminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonClick.setOnClickListener {view ->
            clickDatePicker(view)

        }
    }

    fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{
                    view,year,month,dayOfMonth->
                Toast.makeText(this,"Date selected",Toast.LENGTH_LONG).show()
                val selectedDate="$dayOfMonth/${month+1}/$year"
                binding.textView6.text=selectedDate
                binding.invalidateAll()
                val sdf= SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                val ageinmin= theDate!!.time/60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateMins = currentDate!!.time/60000
                val difference = currentDateMins- ageinmin
                binding.textView8.text=difference.toString()
            },year
            ,month
            ,day)
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()


    }
}