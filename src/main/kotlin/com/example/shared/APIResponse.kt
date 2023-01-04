package com.example.shared

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class APIResponse<T>(
    val systemCode: String,
    val message: String,
    val httpCode: String,
    val data: List<T>
)