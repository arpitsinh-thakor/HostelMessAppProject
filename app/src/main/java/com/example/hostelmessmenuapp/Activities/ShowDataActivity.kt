package com.example.hostelmessmenuapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hostelmessmenuapp.Testing.DataByDateActivity
import com.example.hostelmessmenuapp.databinding.ActivityMainBinding
import com.example.hostelmessmenuapp.databinding.ActivityShowDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShowDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowDataBinding
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn = binding.button
        val editText = binding.editText
        val textView = binding.textView

        val btnCombinedData = binding.btnCombinedData
        btnCombinedData.setOnClickListener {
            val intent = Intent(this@ShowDataActivity, CombinedDataActivity::class.java)
            startActivity(intent)
        }
        val btnDataByDate = binding.btnDatabyDate
        btnDataByDate.setOnClickListener {
            val intent  = Intent(this@ShowDataActivity, DataByDateActivity::class.java)
            startActivity(intent)
        }



        btn.setOnClickListener {
            var menu: String = ""
            ref = FirebaseDatabase.getInstance().reference
            val id = editText.text.toString()
            var idRef = ref.child("breakfast").child(id)
            idRef.get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val snapshot = task.result
                    val day = snapshot.child("day").getValue(Long::class.java)
                    val item = snapshot.child("foodList").getValue(String::class.java)
                    menu = "$menu$day  $item \n"
                    Toast.makeText(this, "Found -> $day $item", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
                }
            }

            idRef = ref.child("lunch").child(id)
            idRef.get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val snapshot = task.result
                    val day = snapshot.child("day").getValue(Long::class.java)
                    val item = snapshot.child("food").getValue(String::class.java)
                    menu = "$menu$day  $item \n"
                    Toast.makeText(this, "Found -> $day $item", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
                }
            }

            idRef = ref.child("dinner").child(id)
            idRef.get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val snapshot = task.result
                    val day = snapshot.child("day").getValue(Long::class.java)
                    val item = snapshot.child("foodList").getValue(String::class.java)
                    menu = "$menu$day  $item \n"
                    textView.text = menu
                    Toast.makeText(this, "Found -> $day $item", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}