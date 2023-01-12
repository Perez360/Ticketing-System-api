package com.example.dtos

class DeleteUserParams(
    val csrf_userid: Int,
    val csrf_token: String,
    val userID:Int
)