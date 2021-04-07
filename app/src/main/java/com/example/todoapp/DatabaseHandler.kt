package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.security.Key

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoDatabase"
        private const val TABLE_NAME = "TodoTable"

        private const val KEY_ID = "_id"
        private  const val KEY_TITLE = "title"
        private const val KEY_IS_CHECKED ="isChecked"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TODO_TABLE = ("CREATE TABLE "+ TABLE_NAME+"("
                + KEY_ID+" INTEGER PRIMARY KEY, "+ KEY_TITLE +" TEXT,"
                + KEY_IS_CHECKED + " INTEGER"+")")
        db?.execSQL(CREATE_TODO_TABLE);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(db)
    }

    fun addTodo(todo:Todo):Long{
        val db= this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(KEY_TITLE,todo.title)
        contentValues.put(KEY_IS_CHECKED,todo.isChecked)
        val success =db.insert(TABLE_NAME,null,contentValues)
        db.close()

        return success
    }

    fun viewTodo(): ArrayList<Todo>{
        val todoList:ArrayList<Todo> =ArrayList<Todo>()
        val selectQuery ="SELECT * FROM $TABLE_NAME"

        val db=this.readableDatabase
        var cursor : Cursor?=null;

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e:SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var title:String
        var isChecked:Int
        if(cursor.moveToFirst()){
            do{
                id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title=cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                isChecked=cursor.getInt(cursor.getColumnIndex(KEY_IS_CHECKED))

                val todo= Todo(id,title,isChecked)
                todoList.add(todo)
            }while(cursor.moveToNext())
        }
        return todoList
    }

    fun updateTodo(todo:Todo):Int{
        val db = this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(KEY_TITLE,todo.title)
        contentValues.put(KEY_IS_CHECKED,todo.isChecked)

        val success =db.update(TABLE_NAME,contentValues, KEY_ID + "="+todo._id,null)

        db.close()
        return success
    }

    fun deleteTodo(todo:Todo):Int{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(KEY_ID,todo._id)
        val success=db.delete(TABLE_NAME, KEY_ID + "=" +todo._id, null)

        db.close()
        return success
    }
}