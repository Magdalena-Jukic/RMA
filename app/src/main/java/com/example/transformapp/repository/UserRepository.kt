package com.example.transformapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Member
import com.example.transformapp.model.Training
import com.example.transformapp.model.User

interface UserRepository {
    fun save(user: User)
    fun isRegistered(email: String, password:String)
    fun getAllUsers():MutableLiveData<MutableList<User>>
}