package com.example.hostelmessmenuapp.Activities

import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Adapter.BreakfastAdapter
import com.example.hostelmessmenuapp.Adapter.CombinedDataAdapter
import com.example.hostelmessmenuapp.Adapter.DinnerAdapter
import com.example.hostelmessmenuapp.Adapter.LunchAdapter
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.R
import com.example.hostelmessmenuapp.Testing.DataByDate
import com.example.hostelmessmenuapp.databinding.ActivityCombinedDataBinding
import com.example.hostelmessmenuapp.databinding.ActivityShowDataBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class CombinedDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCombinedDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference

    private lateinit var recyclerViewCombinedData: RecyclerView
    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var dinnerList: ArrayList<DataDinner>

    lateinit var objList: ArrayList<DataByDate>
    var date: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombinedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        date = "$day-${month+1}-$year"

        recyclerViewCombinedData = binding.rvCombinedData
        recyclerViewCombinedData.layoutManager = LinearLayoutManager(this)
        recyclerViewCombinedData.setHasFixedSize(true)

        breakfastList = arrayListOf<DataBreakfast>()
//        getUserData()

        lunchList = arrayListOf<DataLunch>()
//        getUserData2()

        dinnerList = arrayListOf<DataDinner>()
        objList = arrayListOf()
        getUserData3()

    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("breakfast")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key!! >= date){
                            val user = userSnapshot.getValue(DataBreakfast::class.java)
                            breakfastList.add(user!!)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getUserData2() {
        database2 = FirebaseDatabase.getInstance().getReference("lunch")
        database2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key!! >= date){
                            val user = userSnapshot.getValue(DataLunch::class.java)
                            lunchList.add(user!!)
                        }
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
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key!! >= date){
                            val user = userSnapshot.getValue(DataByDate::class.java)
                            objList.add(user!!)
                        }
                    }
                }
                recyclerViewCombinedData.adapter = CombinedDataAdapter(breakfastList, lunchList, dinnerList, date, objList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}