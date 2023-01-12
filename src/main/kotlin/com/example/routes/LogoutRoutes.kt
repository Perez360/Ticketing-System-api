package com.example.routes

import com.example.shared.APIResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureLogout() {
    routing {
        post("/api/v1/account/logout") {
            call.respond(
                APIResponse<String>(
                    HttpStatusCode.OK.value, "You are logged out", listOf()
                )
            )
        }
    }
}

