package com.example.dtos.user

class GetUserInformationDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val email: String,
)