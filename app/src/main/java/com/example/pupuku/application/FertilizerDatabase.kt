package com.example.pupuku.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pupuku.dao.FertilizerDao
import com.example.pupuku.model.Fertilizer

@Database(entities = [Fertilizer::class], version = 3, exportSchema = false)
abstract class FertilizerDatabase: RoomDatabase() {
    abstract fun FertilizerDao(): FertilizerDao

    companion object{
        private var INSTANCE: FertilizerDatabase?= null

        //migrasi databse versi 2 ke 3, karena ada perubahan data
        private val migration2To3: Migration = object: Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE fertilizer_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE fertilizer_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): FertilizerDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(

                context.applicationContext,
                FertilizerDatabase::class.java,
                "fertilizer_database"
                )
                    .addMigrations(migration2To3    )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}