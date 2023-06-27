package com.example.pupuku.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pupuku.model.Fertilizer
import kotlinx.coroutines.flow.Flow

@Dao
interface FertilizerDao {
    @Query("SELECT * FROM 'Fertilizer_table' ORDER BY name ASC")
    fun getAllTire(): Flow<List<Fertilizer>>

    @Insert
    suspend fun insertTire(fertilizer: Fertilizer)

    @Delete
    suspend fun deleteTire(fertilizer: Fertilizer)

    @Update fun updateTire(fertilizer: Fertilizer)
}