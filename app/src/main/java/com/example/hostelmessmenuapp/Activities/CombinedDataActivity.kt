package com.example.hostelmessmenuapp.Activities

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
import com.example.hostelmessmenuapp.databinding.ActivityCombinedDataBinding
import com.example.hostelmessmenuapp.databinding.ActivityShowDataBinding
import com.google.firebase.database.*

class CombinedDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCombinedDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference

    private lateinit var recyclerViewCombinedData: RecyclerView
    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var dinnerList: ArrayList<DataDinner>

    var day: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombinedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewCombinedData = binding.rvCombinedData
        recyclerViewCombinedData.layoutManager = LinearLayoutManager(this)
        recyclerViewCombinedData.setHasFixedSize(true)

        breakfastList = arrayListOf<DataBreakfast>()
        getUserData()

        lunchList = arrayListOf<DataLunch>()
        getUserData2()

        dinnerList = arrayListOf<DataDinner>()
        getUserData3()

    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("breakfast")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key?.toInt()!! >= day){
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
                        if(userSnapshot.key?.toInt()!! >= day){
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
        database3 = FirebaseDatabase.getInstance().getReference("dinner")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key?.toInt()!! >= day){
                            val user = userSnapshot.getValue(DataDinner::class.java)
                            dinnerList.add(user!!)
                        }
                    }
                }
                recyclerViewCombinedData.adapter = CombinedDataAdapter(breakfastList, lunchList, dinnerList, day)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}