package com.example.plugins

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.server.application.*

fun Application.configureDependencyInjection() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    val userController: UserController = kodein.instance()
}