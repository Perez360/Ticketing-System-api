package com.example.dtos.ticket

class CreateTicketDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val title: String,
    val authorName: String,
    val authorEmail: String,
    val imageURL: String,
    val apiKey: String,
)