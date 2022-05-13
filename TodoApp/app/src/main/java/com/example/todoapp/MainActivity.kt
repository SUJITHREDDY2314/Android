package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var todoList = mutableListOf(
            Todo("Follow me",true),
            Todo("Sujith",false),
            Todo("Vittu",true),
            Todo("Sumanth",false),
            Todo("Vamshi",true)

        )
        val adapter = TodoAdapter(todoList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.button.setOnClickListener {
            val title = binding.textView.text.toString()
            var flag = -1
            var i:Todo
            for(i in todoList){
                if(i.title== title){
                    flag =0
                    break

                }
            }
            if(flag==0){
                Toast.makeText(this,"Cannot add duplicate element", Toast.LENGTH_SHORT).show()
            }
            else{

                val addTodoObj = Todo(title,false)
                todoList.add(addTodoObj)
                adapter.notifyItemInserted(todoList.size-1)
            }
            binding.textView.text.clear()

        }
        //setContentView(R.layout.activity_main)
    }
}