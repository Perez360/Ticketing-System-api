package com.example.dtos.user

class GetMyInformationDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val email: String,
)