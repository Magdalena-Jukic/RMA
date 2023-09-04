package com.example.transformapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transformapp.model.Member
import com.example.transformapp.model.Training
import com.example.transformapp.model.User
import com.example.transformapp.repository.MemberRepositoryImpl
import com.example.transformapp.repository.TrainingRepositoryImpl
import com.example.transformapp.repository.UserRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TransformViewModel (
    private val memberRepositoryImpl: MemberRepositoryImpl,
    private val trainingRepositoryImpl: TrainingRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl

): ViewModel()
{

    var members = memberRepositoryImpl.getAllMembers()
    var trainings = trainingRepositoryImpl.getAllTrainings()
    private var firebaseAuth = Firebase.auth
    var users: MutableLiveData<MutableList<User>>
    init {
        users = userRepositoryImpl.getAllUsers()
    }

    fun save(member: Member){
        member.userID=firebaseAuth.currentUser!!.uid
        memberRepositoryImpl.save(member)
        members = memberRepositoryImpl.getAllMembers()
    }
    fun delete(member: Member){
        memberRepositoryImpl.delete(member)
        members = memberRepositoryImpl.getAllMembers()
    }
    fun getMemberById(id : String):Member?{
        return memberRepositoryImpl.getMemberById(id)
    }



    fun save(training: Training){
        trainingRepositoryImpl.save(training)
        trainings = trainingRepositoryImpl.getAllTrainings()
    }
    fun delete(training: Training){
        trainingRepositoryImpl.delete(training)
        trainings = trainingRepositoryImpl.getAllTrainings()
    }
    fun getTrainingById(id : String): Training?{
        return id?.let { trainingRepositoryImpl.getTrainingById(it) }
    }



    fun saveUser(user: User){
        userRepositoryImpl.save(user)
    }
    fun loginUser(email: String, password: String){
        userRepositoryImpl.isRegistered(email, password)
    }



}