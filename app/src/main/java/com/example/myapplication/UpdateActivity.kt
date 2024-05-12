package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAddTasksBinding
import com.example.myapplication.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DatabaseHelper
    private var taskId: Int= -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_update)

        db= DatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        Log.d("UpdateActivity", "Task ID: $taskId") // Check taskId value in logcat

        if (taskId==-1){
            finish()
            return
        }

        val task= db.getTasksById(taskId)
        binding.EditTask.setText(task.title)
        binding.EditDescription.setText(task.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle= binding.EditTask.text.toString()
            val newContent= binding.EditDescription.text.toString()
            val updateTask= Task(taskId, newTitle,newContent)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this,"changes saved",Toast.LENGTH_SHORT).show()


        }


    }
}