package com.example.dtos.article

class AddArticleDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val title: String,
    val content: String,
    val authorName: String,
    val imageURL: String?,
    val isPrivate:Boolean
)