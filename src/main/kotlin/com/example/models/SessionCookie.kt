package com.example.models

import kotlinx.serialization.Serializable

@Serializable
class SessionCookie(
    val id: Int = 100,
    val userId: Int,
    val token: String?,
    val ip: String?,
    val creationDate: String?,
//    val expiryDate: String,
)