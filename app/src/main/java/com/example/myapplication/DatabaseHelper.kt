package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}