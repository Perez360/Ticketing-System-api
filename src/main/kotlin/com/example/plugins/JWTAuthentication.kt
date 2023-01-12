package com.example.plugins

import com.example.components.security.jwt_auth.JWTAuth
import com.example.shared.APIResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


fun Application.configureJWTAuthentication() {
    JWTAuth.initialised("myapi")
    install(Authentication) {
        jwt {
            verifier(JWTAuth.jwtAuth.verifier)
            validate {

                val claim = it.payload.getClaim("email")
                if (claim != null) {
                    JWTPrincipal(payload = it.payload)
                } else {
                    null
                }
            }
            challenge{
                defaultScheme, realm ->
                call.respond(
                    APIResponse<String>(
                        HttpStatusCode.Unauthorized.value,"NO_PERMISSION", listOf()
                    )
                )
            }
        }
    }
}