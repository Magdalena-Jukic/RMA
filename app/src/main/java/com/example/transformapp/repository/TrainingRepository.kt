package com.example.transformapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Training

interface TrainingRepository {
    fun save(training: Training)
    fun delete(training: Training)
    fun getTrainingById(id: String): Training?
    fun getAllTrainings(): MutableLiveData<MutableList<Training>?>
}