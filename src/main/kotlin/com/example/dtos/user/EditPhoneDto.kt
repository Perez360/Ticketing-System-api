package com.example.dtos.user

class EditPhoneDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val oldPhoneNumber: String,
    val newPhoneNumber: String
)