package com.example.hostelmessmenuapp.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Menu :: class], version = 2)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object{

        @Volatile
        private var INSTANCE : MenuDatabase? = null
        fun getInstance(context: Context): MenuDatabase {
            if (INSTANCE == null) {
                synchronized(MenuDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MenuDatabase::class.java,
                "menu_database2"
            ).build()

        fun getDatabase(context: Context): MenuDatabase{


            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "menu_database"
                ).build()
                INSTANCE = instance
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                return instance

            }

        }
    }
}