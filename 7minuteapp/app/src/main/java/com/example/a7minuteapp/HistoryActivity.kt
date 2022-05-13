package com.example.a7minuteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
//    var timeList:ArrayList<TimeData>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.historyToolbar)
        val actionbar = supportActionBar
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        binding.historyToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val dbObj = SqlDatabase(this,null)
        val listReturned = dbObj.getDbList()

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        var historyAdapter = HistoryAdapter(this,listReturned)
        binding.historyRecyclerView.adapter = historyAdapter

//        timeList!!.add(TimeData(2,"two"))
//        historyAdapter.notifyDataSetChanged()

    }


}