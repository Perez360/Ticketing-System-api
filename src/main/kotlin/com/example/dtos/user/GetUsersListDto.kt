package com.example.dtos.user

class GetUsersListDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val search: String,
)