package com.danilosp.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Todo (

    @PrimaryKey(autoGenerate = true)
    val uuid: Int? = null,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title : String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description : String,

    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    var createdDate : String,

    @ColumnInfo(name = "done")
    @SerializedName("done")
    var done : Boolean = false

)

