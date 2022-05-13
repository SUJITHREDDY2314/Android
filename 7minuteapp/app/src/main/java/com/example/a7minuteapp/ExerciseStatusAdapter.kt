package com.example.a7minuteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val items:ArrayList<ExerciseModel>, private val context:Context):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root){

        val tvItem = binding.tvItemTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.tvItem.text = model.exerciseNo.toString()
        if(model.isSelected){
            holder.tvItem.setBackgroundResource(R.drawable.item_circular_color_accent_background_for_bottomprogress)
        }
        else if(model.isCompleted){
            holder.tvItem.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
        }
        else{
            holder.tvItem.setBackgroundResource(R.drawable.ic_circle_graybg_for_rview)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
}