package com.example.hostelmessmenuapp.Adapter

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.Notification.AlarmReceiver
import com.example.hostelmessmenuapp.R
import com.example.hostelmessmenuapp.Testing.DataByDate
import java.util.Calendar
import kotlin.math.min

class CombinedDataAdapter(var breakfastList: ArrayList<DataBreakfast>,
                            var lunchList: ArrayList<DataLunch>,
                                var dinnerList: ArrayList<DataDinner>,
                                    var DAY: String,
                                        var objList: ArrayList<DataByDate>)
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
        return objList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val breakfast = objList[position].breakfast
        val lunch = objList[position].lunch
        val dinner = objList[position].dinner
        holder.tvBreakfast.text = breakfast.toString()
        holder.tvLunch.text = lunch.toString()
        holder.tvDinner.text = dinner.toString()
        holder.day.text = "Day -> ${position} + $DAY"
    }


}