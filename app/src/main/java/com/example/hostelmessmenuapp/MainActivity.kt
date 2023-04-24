package com.example.hostelmessmenuapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var recyclerViewBreakFast: RecyclerView
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var recyclerViewLunch: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBreakFast = findViewById<RecyclerView>(R.id.rvBreakfast)
        recyclerViewBreakFast.layoutManager = LinearLayoutManager(this)

        val breakfastFood = arrayOf("BREAKFAST1", "BREAKFAST2", "BREAKFAST3", "BREAKFAST4", "BREAKFAST")
        breakfastList = arrayListOf()
        for(index in breakfastFood.indices){
            val item = DataBreakfast(breakfastFood[index])
            breakfastList.add(item)
        }

        var breakfastAdapter = BreakfastAdapter(breakfastList, this)
        recyclerViewBreakFast.adapter = breakfastAdapter


        recyclerViewLunch = findViewById<RecyclerView>(R.id.rvLunch)
        recyclerViewLunch.layoutManager = LinearLayoutManager(this)

        val lunchFood = arrayOf("LUNCH1", "LUNCH2", "LUNCH3", "LUNCH4", "LUNCH5")
        lunchList = arrayListOf()
        for(index in lunchFood.indices){
            val item = DataLunch(lunchFood[index])
            lunchList.add(item)
        }

        var lunchAdapter = LunchAdapter(lunchList, this)
        recyclerViewLunch.adapter = lunchAdapter
    }
}