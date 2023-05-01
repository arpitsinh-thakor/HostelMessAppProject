package com.example.hostelmessmenuapp.Testing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Adapter.CombinedDataAdapter
import com.example.hostelmessmenuapp.R
import kotlin.math.min

class DataByDateAdapter(var breakfastList: List<String>,
                           var lunchList: List<String>,
                                var dinnerList: List<String>,
                                    var date: String)
    :RecyclerView.Adapter<DataByDateAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvBreakfast: TextView = itemView.findViewById(R.id.breakfastItem)
        val tvLunch: TextView = itemView.findViewById(R.id.lunchItem)
        val tvDinner: TextView = itemView.findViewById(R.id.dinnerItem)
        val tvDay: TextView = itemView.findViewById(R.id.dayCombined)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_day2, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return  min( breakfastList.size, min( lunchList.size, dinnerList.size))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr1 = breakfastList
        val curr2 = lunchList
        val curr3 = dinnerList
        holder.tvBreakfast.text = curr1[position]
        holder.tvLunch.text = curr2.toString()
        holder.tvDinner.text = curr3.toString()
        holder.tvDay.text = date
    }
}