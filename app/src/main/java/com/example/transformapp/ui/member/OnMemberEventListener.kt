package com.example.transformapp.ui.member

import com.example.transformapp.model.Member

interface OnMemberEventListener {

    fun onMemberSelected(id: String?)
    fun onMemberLongPress(member: Member?): Boolean

}