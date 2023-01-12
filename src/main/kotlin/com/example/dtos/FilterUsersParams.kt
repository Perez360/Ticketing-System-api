package com.example.dtos

class FilterUsersParams(
    val csrf_userid: Int,
    val csrf_token: String,
    val byName:String?
)