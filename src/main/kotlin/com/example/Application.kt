package com.example

import com.example.components.user.web.configureRouting
import com.example.core.plugins.configureDependencyInjection
import com.example.services.DatabaseFactory
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
    configureSerialization()
    configureRouting()
}
