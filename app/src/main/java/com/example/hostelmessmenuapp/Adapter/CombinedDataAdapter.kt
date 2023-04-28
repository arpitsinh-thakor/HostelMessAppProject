package com.example.hostelmessmenuapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.R
import kotlin.math.min

class CombinedDataAdapter(var breakfastList: ArrayList<DataBreakfast>,
                            var lunchList: ArrayList<DataLunch>,
                                var dinnerList: ArrayList<DataDinner>,
                                    var DAY: Int)
    :RecyclerView.Adapter<CombinedDataAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvBreakfast: TextView = itemView.findViewById(R.id.breakfastItem)
        val tvLunch: TextView = itemView.findViewById(R.id.lunchItem)
        val tvDinner: TextView = itemView.findViewById(R.id.dinnerItem)
        val day: TextView = itemView.findViewById(R.id.dayCombined)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_day2, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return min( breakfastList.size, min( lunchList.size, dinnerList.size))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr1 = breakfastList[position]
        val curr2 = lunchList[position]
        val curr3 = dinnerList[position]
        holder.tvBreakfast.text = curr1.foodList
        holder.tvLunch.text = curr2.food
        holder.tvDinner.text = curr3.foodList
        holder.day.text = "Day -> ${position + DAY}"
    }
}