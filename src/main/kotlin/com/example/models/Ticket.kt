package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Int,
    val title: String,
    val content: String,
    val status: String,
    val dateCreated:String,
    val comments:List<Comment>
    )