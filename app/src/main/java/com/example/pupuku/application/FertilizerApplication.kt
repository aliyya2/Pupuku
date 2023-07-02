package com.example.pupuku.application

import android.app.Application
import com.example.pupuku.repository.FertilizerRepository

class FertilizerApplication: Application() {
    val database by lazy { FertilizerDatabase.getDatabase(this) }
    val repository by lazy { FertilizerRepository(database.FertilizerDao()) }
}