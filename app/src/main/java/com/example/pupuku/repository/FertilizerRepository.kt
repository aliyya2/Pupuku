package com.example.pupuku.repository

import com.example.pupuku.dao.FertilizerDao
import com.example.pupuku.model.Fertilizer
import kotlinx.coroutines.flow.Flow

class FertilizerRepository(private val fertilizerDao: FertilizerDao) {
    val allTires: Flow<List<Fertilizer>> = fertilizerDao.getAllTire()
    suspend fun insertTire(fertilizer: Fertilizer){
        fertilizerDao.insertTire(fertilizer)
    }

    suspend fun deleteTire(fertilizer: Fertilizer){
        fertilizerDao.deleteTire(fertilizer)
    }

    suspend fun updateTire(fertilizer: Fertilizer){
        fertilizerDao.updateTire(fertilizer)
    }
}