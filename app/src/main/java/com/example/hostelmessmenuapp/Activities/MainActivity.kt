package com.example.hostelmessmenuapp.Activities

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmessmenuapp.Adapter.BreakfastAdapter
import com.example.hostelmessmenuapp.Adapter.DinnerAdapter
import com.example.hostelmessmenuapp.Adapter.LunchAdapter
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.Notification.AlarmReceiver
import com.example.hostelmessmenuapp.RoomDatabase.MenuDatabase
import com.example.hostelmessmenuapp.databinding.ActivityMainBinding
import com.google.firebase.database.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference

    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var recyclerViewBreakFast: RecyclerView
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var recyclerViewLunch: RecyclerView
    lateinit var dinnerList: ArrayList<DataDinner>
    lateinit var recyclerViewDinner: RecyclerView

    lateinit var ref: DatabaseReference

    lateinit var notificationCalender: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    private lateinit var menuDb : MenuDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        menuDb = MenuDatabase.getDatabase(this)
        createNotificationChannel()


        binding.btn.setOnClickListener {
            val intent = Intent(this, ShowDataActivity::class.java)
            startActivity(intent)
        }


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

        recyclerViewDinner = binding.rvDinner
        recyclerViewDinner.layoutManager = LinearLayoutManager(this)
        recyclerViewDinner.setHasFixedSize(true)

        dinnerList = arrayListOf<DataDinner>()
        getUserData3()

        ref = FirebaseDatabase.getInstance().reference
        val id = 1
        val idRef = ref.child("breakfast").child(id.toString())
        idRef.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val snapshot = task.result
                val day = snapshot.child("day").getValue(Long::class.java)
                val item = snapshot.child("foodList").getValue(String::class.java)
                Toast.makeText(this, "Found -> $day $item", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
            }
        }

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
//        var breakfastAdapter = BreakfastAdapter(breakfastList)
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
//        var lunchAdapter = LunchAdapter(lunchList)
//        recyclerViewLunch.adapter = lunchAdapter
//
//        recyclerViewLunch = findViewById<RecyclerView>(R.id.rvLunch)
//        recyclerViewLunch.layoutManager = LinearLayoutManager(this)
//
//        val dinnerFood = arrayOf("DINNER1", "DINNER2", "DINNER3", "DINNER4", "DINNER5", "DINNER6", "DINNER7", "DINNER8", "DINNER9", "DINNER10")
//        dinnerList = arrayListOf()
//        for(index in dinnerFood.indices){
//            val item = DataDinner(index, dinnerFood[index])
//            dinnerList.add(item)
//        }
//
//        var dinnerAdapter = DinnerAdapter(dinnerList)
//        recyclerViewLunch.adapter = dinnerAdapter
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
//
//        database = FirebaseDatabase.getInstance().getReference("dinner")
//        for(index in dinnerFood.indices){
//            val item = DataDinner(index, dinnerFood[index])
//            database.child(index.toString()).setValue(item).addOnSuccessListener {
//                Toast.makeText(this, "$index dinner added", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "$index dinner not added", Toast.LENGTH_SHORT).show()
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

    private fun getUserData3() {
        database3 = FirebaseDatabase.getInstance().getReference("dinner")
        database3.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(DataDinner::class.java)
                        dinnerList.add(user!!)
                    }
                    recyclerViewDinner.adapter = DinnerAdapter(dinnerList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun createNotificationChannel() {
        var cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        val date = "$day-${month+1}-$year"
        var desBreakfast:String = ":"
        var desLunch:String = ":"
        var desDinner:String = ":"
        GlobalScope.launch(Dispatchers.IO) {
            desBreakfast = menuDb.menuDao().find(date).breakFast ?: ""
            desLunch = menuDb.menuDao().find(date).lunch ?: ""
            desDinner = menuDb.menuDao().find(date).dinner ?: ""
        }
        Toast.makeText(this@MainActivity, "date -> $date", Toast.LENGTH_LONG).show()
        Toast.makeText(this@MainActivity, "des -> $desBreakfast", Toast.LENGTH_SHORT).show()

        val name :CharSequence = "it's notification Channel"
        val description = desBreakfast
        val importance  = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Arpit", name, importance)
        channel.description = description.toString()
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
        cal.clear()
        setTime(23, 9)
        setAlarm(desBreakfast)

        cal = Calendar.getInstance()
        val channel2 = NotificationChannel("Arpit", name, importance)
        channel2.description = description.toString()
        val notificationManager2 = getSystemService(
            NotificationManager::class.java
        )
        notificationManager2.createNotificationChannel(channel)
        cal.clear()
        setTime(23, 10)
        setAlarm(desLunch)

        cal = Calendar.getInstance()
        val channel3 = NotificationChannel("Arpit", name, importance)
        channel3.description = description.toString()
        val notificationManager3 = getSystemService(
            NotificationManager::class.java
        )
        notificationManager3.createNotificationChannel(channel)
        cal.clear()
        setTime(23, 11)
        setAlarm(desDinner)
    }



    private fun setTime(hour_of_day: Int, minute: Int) {
        notificationCalender = Calendar.getInstance()
        notificationCalender.set(Calendar.HOUR_OF_DAY, hour_of_day)
        notificationCalender.set(Calendar.MINUTE, minute)
        notificationCalender.set(Calendar.SECOND, 0)
        notificationCalender.set(Calendar.MILLISECOND, 0)
    }
    private fun setAlarm(des: String) {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("des" , des)
        pendingIntent = PendingIntent.getBroadcast(this, (0..2147483647).random(), intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, notificationCalender.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarm set for $des -> ${notificationCalender.timeInMillis}", Toast.LENGTH_SHORT).show()
    }
}

