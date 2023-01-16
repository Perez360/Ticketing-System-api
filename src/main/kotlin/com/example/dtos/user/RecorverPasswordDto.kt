package com.example.dtos.user

class RecorverPasswordDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val oldPassword: String,
    val newPassword: String
)