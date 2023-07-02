package com.example.pupuku.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pupuku.dao.FertilizerDao
import com.example.pupuku.model.Fertilizer

@Database(entities = [Fertilizer::class], version = 2, exportSchema = false)
abstract class FertilizerDatabase: RoomDatabase() {
    abstract fun FertilizerDao(): FertilizerDao

    companion object{
        private var INSTANCE: FertilizerDatabase?= null

        fun getDatabase(context: Context): FertilizerDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(

                context.applicationContext,
                FertilizerDatabase::class.java,
                "fertilizer_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}