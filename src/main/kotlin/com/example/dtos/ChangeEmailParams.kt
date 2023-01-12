package com.example.dtos

class ChangeEmailParams(
    val csrf_userid: Int,
    val csrf_token: String,
    val newEmail:String
)