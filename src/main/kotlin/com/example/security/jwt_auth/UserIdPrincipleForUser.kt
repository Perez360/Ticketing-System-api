package com.example.security.jwt_auth

import io.ktor.auth.*


data class UserEmailPrincipleForUser(
    val id: Int) : Principal