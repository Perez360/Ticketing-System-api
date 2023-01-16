package com.example.dtos.user

class VerifyEmailDto(
    val userEmail: String,
    val verificationToken: String,
)