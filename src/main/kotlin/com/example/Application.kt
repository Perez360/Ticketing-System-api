package com.example

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.example.components.user.dao.UserDao
import com.example.components.user.web.configureRouting
import com.example.services.DatabaseFactory
import configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    DatabaseFactory.connect(environment.config)

    //configureDependencyInjection()
    configureRouting()
}
