package com.example.dtos.article

class DeleteArticleDto(
    val csrf_userid: Int,
    val csrf_token: String,
    val article_id: Int
)