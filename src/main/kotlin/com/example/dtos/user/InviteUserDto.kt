package com.example.dtos.user

class InviteUserDto(
    val csrf_userid: Int,
    val csrf_token: Int,
    val inviteeName: String,
    val inviteeEmail: String,
)