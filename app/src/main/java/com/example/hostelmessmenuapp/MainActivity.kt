package com.example.hostelmessmenuapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference

    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var recyclerViewBreakFast: RecyclerView
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var recyclerViewLunch: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerViewBreakFast = binding.rvBreakfast
        recyclerViewBreakFast.layoutManager = LinearLayoutManager(this)
        recyclerViewBreakFast.setHasFixedSize(true)

         breakfastList = arrayListOf<DataBreakfast>()
         getUserData()

        recyclerViewLunch = binding.rvLunch
        recyclerViewLunch.layoutManager = LinearLayoutManager(this)
        recyclerViewLunch.setHasFixedSize(true)

        lunchList = arrayListOf<DataLunch>()
       getUserData2()





//        recyclerViewBreakFast = findViewById<RecyclerView>(R.id.rvBreakfast)
//        recyclerViewBreakFast.layoutManager = LinearLayoutManager(this)
//
//        val breakfastFood = arrayOf("BREAKFAST1", "BREAKFAST2", "BREAKFAST3", "BREAKFAST4", "BREAKFAST5", "BREAKFAST6", "BREAKFAST7", "BREAKFAST8" ,"BREAKFAST9",  "BREAKFAST10",)
//
//        breakfastList = arrayListOf()
//        for(index in breakfastFood.indices){
//            val item = DataBreakfast(index, breakfastFood[index])
//            breakfastList.add(item)
//        }
//
//        var breakfastAdapter = BreakfastAdapter(breakfastList, this)
//        recyclerViewBreakFast.adapter = breakfastAdapter
//
//
//        recyclerViewLunch = findViewById<RecyclerView>(R.id.rvLunch)
//        recyclerViewLunch.layoutManager = LinearLayoutManager(this)
//
//        val lunchFood = arrayOf("LUNCH1", "LUNCH2", "LUNCH3", "LUNCH4", "LUNCH5", "LUNCH6", "LUNCH7", "LUNCH8", "LUNCH9", "LUNCH10")
//        lunchList = arrayListOf()
//        for(index in lunchFood.indices){
//            val item = DataLunch(index, lunchFood[index])
//            lunchList.add(item)
//        }
//
//        var lunchAdapter = LunchAdapter(lunchList, this)
//        recyclerViewLunch.adapter = lunchAdapter
//
//
//        database = FirebaseDatabase.getInstance().getReference("breakfast")
//        for(index in breakfastFood.indices){
//            val item = DataBreakfast(index, breakfastFood[index])
//            database.child(index.toString()).setValue(item).addOnSuccessListener {
//                Toast.makeText(this, "$index breakfast added", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "$index breakfast not added", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        database = FirebaseDatabase.getInstance().getReference("lunch")
//        for(index in lunchFood.indices){
//            val item = DataLunch(index, lunchFood[index])
//            database.child(index.toString()).setValue(item).addOnSuccessListener {
//                Toast.makeText(this, "$index lunch added", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "$index lunch not added", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("breakfast")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DataBreakfast::class.java)
                        breakfastList.add(user!!)
                    }
                    recyclerViewBreakFast.adapter = BreakfastAdapter(breakfastList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getUserData2() {
        database2 = FirebaseDatabase.getInstance().getReference("lunch")
        database2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DataLunch::class.java)
                        lunchList.add(user!!)
                    }
                    recyclerViewLunch.adapter = LunchAdapter(lunchList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}