package com.example.models

import kotlinx.serialization.Serializable

@Serializable
class APIKey(
    val id: Int=100,
    val name: String?,
    val token:String?=null,
    val description: String?,
    val dateCreated: String,
    val canCreateUsers: Boolean,
    val canCreateTickets: Boolean,
    val canCheckTickets: Boolean,
    val shouldReturnTicketNumber: Boolean
)