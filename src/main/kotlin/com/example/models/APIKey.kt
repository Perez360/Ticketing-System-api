package com.example.models

import kotlinx.serialization.Serializable

@Serializable
class APIKey(
    val id: Int=100,
    val name: String,
    val authorName: String,
    val token:String?=null,
    val description: String,
    val dateCreated: String,
    val canCreateUsers: Int,
    val canCreateTickets: Int,
    val canCheckTickets: Int,
    val shouldReturnTicketNumber: Int
)