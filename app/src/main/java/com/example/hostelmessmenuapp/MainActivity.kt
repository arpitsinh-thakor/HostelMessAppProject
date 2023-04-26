package com.example.hostelmessmenuapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var recyclerViewBreakFast: RecyclerView
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var recyclerViewLunch: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        recyclerViewBreakFast = findViewById<RecyclerView>(R.id.rvBreakfast)
        recyclerViewBreakFast.layoutManager = LinearLayoutManager(this)

        val breakfastFood = arrayOf("BREAKFAST1", "BREAKFAST2", "BREAKFAST3", "BREAKFAST4", "BREAKFAST5", "BREAKFAST6", "BREAKFAST7", "BREAKFAST8" ,"BREAKFAST9",  "BREAKFAST10",)

        breakfastList = arrayListOf()
        for(index in breakfastFood.indices){
            val item = DataBreakfast(index, breakfastFood[index])
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


        database = FirebaseDatabase.getInstance().getReference("breakfast")
        for(index in breakfastFood.indices){
            val item = DataBreakfast(index, breakfastFood[index])
            database.child(index.toString()).setValue(item).addOnSuccessListener {
                Toast.makeText(this, "$index added", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "$index added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}