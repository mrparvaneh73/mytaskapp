package com.example.usertaskapp.data.localdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "time") val time:String,
    @ColumnInfo(name = "date") val date:String,
    val userOwnerId: Int,
    val done:Boolean=false,
    val doing:Boolean=false
        )
