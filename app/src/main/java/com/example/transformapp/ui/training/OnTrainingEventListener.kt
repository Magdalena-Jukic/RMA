package com.example.transformapp.ui.training

import com.example.transformapp.model.Training

interface OnTrainingEventListener {
    fun onTrainingSelected(id: String?)
    fun onTrainingLongPress(training: Training?): Boolean
}