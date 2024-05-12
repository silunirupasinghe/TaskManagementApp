package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.CaseMap.Title

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION){
    companion object{
        private const val  DATABASE_NAME= "task_app_db"
        private const val  DATABASE_VERSION = 1
        private const val  TABLE_NAME= "tasks"
        private const val  COLUMN_ID= "id"
        private const val  COLUMN_TITLE= "title"
        private const val  COLUMN_content= "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery= "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTERGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_content TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery= "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertTask(task:Task){
        val db= writableDatabase
        val value= ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_content,task.content)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }
    fun getAllTasks(): List<Task>{
        val taskList= mutableListOf<Task>()
        val db= readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while(cursor.moveToNext()){
            val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_content))

            val task= Task(id, title, content)
            taskList.add(task)
        }
        cursor.close()
        db.close()
        return taskList

    }
    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_content, task.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTasksById(taskId: Int): Task{
        val db= readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID= $taskId"
        val cursor= db.rawQuery(query, null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_content))

        cursor.close()
        db.close()
        return Task(id,title,content)
    }

}