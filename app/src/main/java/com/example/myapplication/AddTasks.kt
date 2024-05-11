package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAddTasksBinding

class AddTasks : AppCompatActivity() {

    private lateinit var binding: ActivityAddTasksBinding
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= DatabaseHelper(this)

        binding.addButton.setOnClickListener {
            val title= binding.AddTask.text.toString()
            val content = binding.addDescription.text.toString()
            val task= Task(0, title,content)
            db.insertTask(task)
            finish()
            Toast.makeText(this,"Task Saved", Toast.LENGTH_SHORT).show()
        }
    }
}