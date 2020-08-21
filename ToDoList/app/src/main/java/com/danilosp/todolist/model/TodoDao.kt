package com.danilosp.todolist.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    fun insertAll(vararg dogs: Todo?): List<Long?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(vararg todo: Todo?)

    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid = :todoId")
    fun getTodo(todoId: Int): Todo?

    @Query("DELETE FROM Todo")
    fun deleteAll()

    @Query("DELETE FROM Todo WHERE uuid = :todoId")
    fun deleteOne(todoId: Int)

    @Query("UPDATE Todo SET done = :done WHERE uuid = :todoId")
    fun updateOne(todoId: Int?, done: Boolean)

}