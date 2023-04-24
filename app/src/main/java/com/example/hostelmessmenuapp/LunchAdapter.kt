package com.example.hostelmessmenuapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LunchAdapter(var menuList: ArrayList<DataLunch>, var context: Activity)
    : RecyclerView.Adapter<LunchAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val menuItem: TextView = itemView.findViewById<TextView>(R.id.item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LunchAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_day, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LunchAdapter.MyViewHolder, position: Int) {
        val currItem = menuList[position]
        holder.menuItem.text = currItem.food
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}

