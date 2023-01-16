package com.example.dtos.user

class EditEmailDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val newEmail:String
)