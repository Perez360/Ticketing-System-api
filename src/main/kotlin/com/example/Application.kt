package com.example

import com.example.database.DatabaseFactory
import com.example.routes.configureRouting
import configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    DatabaseFactory.connect()
    embeddedServer(Netty, port = 1010, host = "127.0.0.1") {
        module()
    }.start(wait = true)
}

fun Application.module() {

    environment.config.config("application")
    configureSerialization()
//    configureJWTAuthentication()
    configureRouting()
}
