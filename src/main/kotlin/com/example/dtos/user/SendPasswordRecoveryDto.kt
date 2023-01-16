package com.example.dtos.user

class SendPasswordRecoveryDto(
    val userEmail:String,
    val isStaff:Boolean=false
)