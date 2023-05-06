package com.example.hostelmessmenuapp.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table")
data class Menu(
    @PrimaryKey()
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "breakFast")
    val breakFast:String,
    @ColumnInfo(name = "lunch")
    val lunch:String,
    @ColumnInfo(name = "dinner")
    val dinner:String
)
