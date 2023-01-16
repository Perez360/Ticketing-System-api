package com.example.dtos.user

class FilterUsersDto(
    val csrf_userid: Int,
    val csrf_token: String,
//    val startIndex: Int,
//    val size:Int,
    val byName:String?
)