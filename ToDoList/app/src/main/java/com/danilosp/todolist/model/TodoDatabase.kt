package com.danilosp.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 6)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        var INSTANCE: TodoDatabase? = null

        fun getAppDataBase(context: Context): TodoDatabase? {
            if (INSTANCE == null){
                synchronized(TodoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}