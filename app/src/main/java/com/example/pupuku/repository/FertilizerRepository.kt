package com.example.pupuku.repository

import com.example.pupuku.dao.FertilizerDao
import com.example.pupuku.model.Fertilizer
import kotlinx.coroutines.flow.Flow

class FertilizerRepository(private val fertilizerDao: FertilizerDao) {
    val allTires: Flow<List<Fertilizer>> = fertilizerDao.getAllFertilizer()
    suspend fun insertFertilizer(fertilizer: Fertilizer){
        fertilizerDao.insertFertilizer(fertilizer)
    }

    suspend fun deleteFertilizer(fertilizer: Fertilizer){
        fertilizerDao.deleteFertilizer(fertilizer)
    }

    suspend fun updateFertilizer(fertilizer: Fertilizer){
        fertilizerDao.updateFertilizer(fertilizer)
    }
}