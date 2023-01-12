package com.example.dtos

class CreateAPIKeyParams(
    val csrfUserID: Int,
    val csrfToken: String,
    val name: String,
    val authorID: Int,
    val authorName: String,
    val description: String,
    val dateCreated: String,
    val canCreateUsers: Boolean,
    val canCheckTickets: Boolean,
    val canCreateTickets: Boolean,
    val shouldReturnTicketNumber: Boolean,
)