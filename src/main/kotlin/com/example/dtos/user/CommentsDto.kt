package com.example.dtos.user

data class CommentsDto(
    val csrfUserID: Int,
    val csrfToken: String,
    val title: String,
    val author: String,
    val content: String,
)