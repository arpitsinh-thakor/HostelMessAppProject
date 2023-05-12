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
import com.example.hostelmessmenuapp.Adapter.CombinedDataAdapter
import com.example.hostelmessmenuapp.Data.DataBreakfast
import com.example.hostelmessmenuapp.Data.DataDinner
import com.example.hostelmessmenuapp.Data.DataLunch
import com.example.hostelmessmenuapp.Notification.AlarmReceiver
import com.example.hostelmessmenuapp.RoomDatabase.Menu
import com.example.hostelmessmenuapp.RoomDatabase.MenuDatabase
import com.example.hostelmessmenuapp.Testing.DataByDate
import com.example.hostelmessmenuapp.databinding.ActivityCombinedDataBinding
import com.google.firebase.database.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CombinedDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCombinedDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference
    private lateinit var database4: DatabaseReference

    private lateinit var recyclerViewCombinedData: RecyclerView
    lateinit var  breakfastList: ArrayList<DataBreakfast>
    lateinit var  lunchList: ArrayList<DataLunch>
    lateinit var dinnerList: ArrayList<DataDinner>

    lateinit var objList: ArrayList<DataByDate>
    var date: String = ""

    lateinit var notificationCalender: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    lateinit var breakfastRecord: ArrayList<String>
    lateinit var lunchRecord: ArrayList<String>
    lateinit var dinnerRecord: ArrayList<String>

    private lateinit var menuDb : MenuDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombinedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuDb = MenuDatabase.getDatabase(this)

        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        date = "$day-${month+1}-$year"

        recyclerViewCombinedData = binding.rvCombinedData
        recyclerViewCombinedData.layoutManager = LinearLayoutManager(this)
        recyclerViewCombinedData.setHasFixedSize(true)

        breakfastList = arrayListOf<DataBreakfast>()
        breakfastRecord = arrayListOf()
        getUserData()

        lunchList = arrayListOf<DataLunch>()
        lunchRecord = arrayListOf()
        getUserData2()

        dinnerList = arrayListOf<DataDinner>()
        dinnerRecord = arrayListOf()
        objList = arrayListOf()
        getUserData3()

//        createNotificationChannel()
////        setTime()
//        setAlarm()

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

    @OptIn(DelicateCoroutinesApi::class)
    private fun getUserData3() {
        database3 = FirebaseDatabase.getInstance().getReference("Menu")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
//                        if(userSnapshot.key!! >= date){
                            val user = userSnapshot.getValue(DataByDate::class.java)
                            objList.add(user!!)
                            val menu = Menu(userSnapshot.key.toString(), user.breakfast.toString(), user.lunch.toString(), user.dinner.toString())
                            GlobalScope.launch(Dispatchers.IO) {
                                menuDb.menuDao().insert(menu)
                            }
                            breakfastRecord.add(user.breakfast.toString())
                            lunchRecord.add(user.lunch.toString())
                            dinnerRecord.add(user.dinner.toString())
//                        }
                    }
                }
                recyclerViewCombinedData.adapter = CombinedDataAdapter(breakfastList, lunchList, dinnerList, date, objList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun createNotificationChannel() {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        val date = "$day-${month+1}-$year"
        val name :CharSequence = "it's notification Channel"
        val index = getIndex(day, month, year, date)
        val description = "hello"
        val importance  = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Arpit", name, importance)
        channel.description = description.toString()
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun getIndex(day: Int, month: Int, year: Int, date: String) :Int{
        database4 = FirebaseDatabase.getInstance().getReference("Menu")
        var index = 0
        database4.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(userSnapshot.key!! == date){
                            return
                        }
                        index++
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return index
    }

    private fun setTime() {
        notificationCalender = Calendar.getInstance()
        notificationCalender[Calendar.HOUR_OF_DAY] = 0
        notificationCalender[Calendar.MINUTE] = 9
        notificationCalender[Calendar.SECOND] = 0
        notificationCalender[Calendar.MILLISECOND] = 0
    }
    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(
            AlarmManager.RTC_WAKEUP, notificationCalender.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarm set for ${notificationCalender.timeInMillis}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(menuDb.isOpen) menuDb.close()
    }
}
