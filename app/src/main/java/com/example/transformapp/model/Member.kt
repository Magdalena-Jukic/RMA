package com.example.transformapp.model

import android.net.Uri

data class Member(
    var userID: String,
    var id: String,
    var name: String,
    var surname: String,
    var email: String,
    var phoneNumber: String,
    var picture: Uri
) {

}
