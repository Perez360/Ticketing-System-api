package com.example.dtos

data class CommentsParams(
    val csrfUserID: Int,
    val csrfToken: String,
    val title: String,
    val author: String,
    val content: String,
)