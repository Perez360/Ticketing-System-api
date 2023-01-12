package com.example.dtos

class ChangePhoneParams(
    val csrf_userid: Int,
    val csrf_token: String,
    val oldPhoneNumber: String,
    val newPhoneNumber: String
)