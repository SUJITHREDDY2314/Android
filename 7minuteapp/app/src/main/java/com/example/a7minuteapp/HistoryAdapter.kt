package com.example.a7minuteapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteapp.databinding.RecyclerViewHistoryBinding
import com.google.android.material.snackbar.Snackbar

class HistoryAdapter(val context: Context,val items:ArrayList<DateId>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    init {
        items.sortBy { it.id }
    }
    var removedVal=""
    var removedPos=-1
    class ViewHolder(binding: RecyclerViewHistoryBinding):RecyclerView.ViewHolder(binding.root){
        val rvList = binding.recyclerViewList
        val sno = binding.recyclerViewSno
        val time = binding.recyclerViewTime
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewHistoryBinding.inflate(LayoutInflater.from(context),parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sno.text = (position+1).toString()
        holder.time.text = items[position].date
        if((position+1) % 2 ==0){
            holder.rvList.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else{
            holder.rvList.setBackgroundColor(Color.parseColor("#808080"))
        }
        holder.rvList.setOnLongClickListener{
            val obj=SqlDatabase(context,null)
            val selctedItem=items[position]
            removedVal = items[position].date
            removedPos= items[position].id
            obj.deleteDate(items[position].id)
            Snackbar.make(it,"Deleted",Snackbar.LENGTH_LONG).setAction("Undo"){
                obj.addDate(removedVal,removedPos)
                items.add(DateId(removedPos,removedVal))
                sortMyArray()
                notifyDataSetChanged()
                removedVal=""
                removedPos=-1
            }.show()
            items.remove(items[position])
            notifyDataSetChanged()
            true
        }
    }

    private fun sortMyArray() {
        items.sortBy { it.id }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}