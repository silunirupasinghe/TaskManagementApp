package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyCallback.DataActivityListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var db: DatabaseHelper
    private lateinit var taskAdopter: TaskAdopter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        taskAdopter= TaskAdopter(db.getAllTasks(),this)

        binding.taskRecylerView.layoutManager= LinearLayoutManager(this)
        binding.taskRecylerView.adapter=taskAdopter


        binding.addButton.setOnClickListener {
            val intent= Intent(this,AddTasks::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        taskAdopter.refreshData(db.getAllTasks())
    }
}