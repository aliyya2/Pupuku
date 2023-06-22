package com.example.pupuku.repository

import com.example.pupuku.dao.TireDao
import com.example.pupuku.model.Tire
import kotlinx.coroutines.flow.Flow

class TireRepository(private val tireDao: TireDao) {
    val allTires: Flow<List<Tire>> = tireDao.getAllTire()
    suspend fun insertTire(tire: Tire){
        tireDao.insertTire(tire)
    }

    suspend fun deleteTire(tire: Tire){
        tireDao.deleteTire(tire)
    }

    suspend fun updateTire(tire: Tire){
        tireDao.updateTire(tire)
    }
}