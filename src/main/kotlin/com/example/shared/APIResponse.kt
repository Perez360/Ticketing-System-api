package com.example.shared

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class APIResponse<T>(
    val code: Int,
    val message: String,
    val data: List<T>
)