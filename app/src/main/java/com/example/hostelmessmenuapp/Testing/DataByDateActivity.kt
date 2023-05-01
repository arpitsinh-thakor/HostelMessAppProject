package com.example.hostelmessmenuapp.Testing

import android.icu.util.LocaleData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Adapter.CombinedDataAdapter
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.R
import com.example.hostelmessmenuapp.databinding.ActivityDataByDateBinding
import com.google.firebase.database.*
import java.time.LocalDate
import java.util.*

class DataByDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataByDateBinding

    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference
    var date: String = ""

    lateinit var breakFastList: MutableList<String>
    lateinit var lunchList: MutableList<String>
    lateinit var dinnerList: MutableList<String>

    lateinit var recyclerView: RecyclerView

    lateinit var outputBreakfast: MutableList<String>
    lateinit var outputLunch: MutableList<String>
    lateinit var outputDinner :MutableList<String>

    lateinit var list: MutableList<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataByDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        date = "$day-$month-$year"

        list = mutableListOf()

        outputBreakfast = mutableListOf()
//        getUserData()
        outputLunch = mutableListOf()
//        getUserData2()
        outputDinner = mutableListOf()
        getUserData3()

        breakFastList = mutableListOf()
//        breakFastList.add("Breakfast1")
//        breakFastList.add("Breakfast2")
//        breakFastList.add("Breakfast3")
//        breakFastList.add("Breakfast4")
//        breakFastList.add("Breakfast5")
//
        lunchList = mutableListOf()
//        lunchList.add("Lunch1")
//        lunchList.add("Lunch2")
//        lunchList.add("Lunch3")
//        lunchList.add("Lunch4")
//        lunchList.add("Lunch5")
//
        dinnerList = mutableListOf()
//        dinnerList.add("Dinner1")
//        dinnerList.add("Dinner2")
//        dinnerList.add("Dinner3")
//        dinnerList.add("Dinner4")
//        dinnerList.add("Dinner5")

//        var date = "28-04-2023"
//        database = FirebaseDatabase.getInstance().getReference("Menu")
//        val item = DataByDate(breakFastList, lunchList, dinnerList)
//        database.child(date).setValue(item).addOnSuccessListener {
//                Toast.makeText(this, "$date  added", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "$date  not added", Toast.LENGTH_SHORT).show()
//            }

//        for(i in 28..30){
//            var date = "$i-04-2023"
//            database = FirebaseDatabase.getInstance().getReference("Menu")
//            val item = DataByDate(breakFastList, lunchList, dinnerList)
//            database.child(date).setValue(item).addOnSuccessListener {
//                Toast.makeText(this, "$date  added", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "$date  not added", Toast.LENGTH_SHORT).show()
//            }
//        }

        recyclerView = binding.rvDataByDate
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = DataByDateAdapter(outputBreakfast, outputLunch, outputDinner, date)
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("Menu")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DataByDate::class.java)
//                        breakFastList.
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getUserData2() {
        database2 = FirebaseDatabase.getInstance().getReference("Menu")
        database2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getUserData3() {
        database3 = FirebaseDatabase.getInstance().getReference("Menu")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DataByDate::class.java)
                        if (user != null) {

                        }
                    }
                }
                recyclerView.adapter = DataByDateAdapter(breakFastList, lunchList, dinnerList, date)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}