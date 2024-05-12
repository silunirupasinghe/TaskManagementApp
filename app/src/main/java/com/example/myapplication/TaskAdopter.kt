package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

class TaskAdopter(private var tasks: List<Task>, context: Context):
    RecyclerView.Adapter<TaskAdopter.TaskViewHolder>() {

        private val db: DatabaseHelper= DatabaseHelper(context)


    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView= itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView= itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView= itemView.findViewById(R.id.updateButtonlist)
        val deleteButton: ImageView= itemView.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.task_items, parent, false)
        return  TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task =tasks[position]
        holder.titleTextView.text = task.title
        holder.contentTextView.text= task.content

        holder.updateButton.setOnClickListener{
            val intent= Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("task_id",task.id)
                Log.d("TaskAdapter", "Clicked update for task: ${task.id}")
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshData(newTasks: List<Task>){
        tasks= newTasks
        notifyDataSetChanged()
    }

}