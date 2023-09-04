package com.example.transformapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Member

interface MemberRepository {
    fun save(member: Member)
    fun delete(member: Member)
    fun getMemberById(id: String): Member?
    fun getAllMembers(): MutableLiveData<MutableList<Member>?>
}