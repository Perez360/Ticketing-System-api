package com.example.dtos

class ChangePasswordParams(
    val csrf_userid: Int,
    val csrf_token: String,
    val oldPassword: String,
    val newPassword: String
)