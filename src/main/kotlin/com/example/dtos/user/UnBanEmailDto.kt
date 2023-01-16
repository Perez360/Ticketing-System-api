package com.example.dtos.user

class UnBanEmailDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val emailToUnBan: String,
)