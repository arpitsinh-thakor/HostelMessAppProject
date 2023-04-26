package com.example.hostelmessmenuapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BreakfastAdapter(var menuList: ArrayList<DataBreakfast>)
    :RecyclerView.Adapter<BreakfastAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val menuItem: TextView = itemView.findViewById(R.id.item)
        val day: TextView = itemView.findViewById(R.id.day)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreakfastAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_day, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BreakfastAdapter.MyViewHolder, position: Int) {
        val currItem = menuList[position]
        holder.menuItem.text = currItem.foodList
        holder.day.text = currItem.day.toString()
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}

