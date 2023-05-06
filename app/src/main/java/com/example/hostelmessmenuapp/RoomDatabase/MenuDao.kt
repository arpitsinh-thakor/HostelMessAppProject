package com.example.hostelmessmenuapp.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(menu: Menu)

    @Query("SELECT * FROM menu_table")
    fun get(): List<Menu>

    @Query("SELECT * FROM menu_table WHERE date LIKE :date2")
    fun find(date2: String): Menu
}