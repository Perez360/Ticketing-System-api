package com.example.dtos.user

class DeleteUserDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val userID:Int
)