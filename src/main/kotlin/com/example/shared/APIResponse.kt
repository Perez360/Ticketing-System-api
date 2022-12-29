package com.example.shared

import io.ktor.http.*

data class APIResponse<T>(
    val systemCode: String,
    val message: String,
    val httpCode: HttpStatusCode,
    val data: List<T>
)