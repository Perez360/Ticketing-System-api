package com.example.models

import kotlinx.serialization.Serializable

@Serializable
class Comment (
    val id:Int,
    val authorID:Int,
    val authorName:String,
    val dateCommented:String,
    val content:String
)