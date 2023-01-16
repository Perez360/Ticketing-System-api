package com.example.dtos.user

class BanEmailDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val emailToBan: String,
)