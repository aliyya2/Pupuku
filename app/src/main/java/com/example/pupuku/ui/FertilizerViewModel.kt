package com.example.pupuku.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pupuku.model.Fertilizer
import com.example.pupuku.repository.FertilizerRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FertilizerViewModel(private val repository: FertilizerRepository): ViewModel() {
    val allFertilizer: LiveData<List<Fertilizer>> = repository.allTires.asLiveData()

    fun insert(fertilizer: Fertilizer) = viewModelScope.launch {
        repository.insertFertilizer(fertilizer)
    }

    fun delete(fertilizer: Fertilizer) = viewModelScope.launch {
        repository.deleteFertilizer(fertilizer)
    }

    fun update(fertilizer: Fertilizer) = viewModelScope.launch {
        repository.updateFertilizer(fertilizer)
    }
}

class FertilizerViewModelFactory(private val repository: FertilizerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((FertilizerViewModel::class.java))) {
            return FertilizerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}