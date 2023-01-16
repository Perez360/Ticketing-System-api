package com.example.dtos.user

class ResendUserSignupTokenDto(
    val csrf_userid: Int,
    val email: String,
)