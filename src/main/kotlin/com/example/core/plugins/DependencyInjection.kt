package com.example.core.plugins

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.server.application.*
import io.mockk.mockk

fun Application.configureDependencyInjection() {
    val kodein = Kodein {
        //bind<UserController>() with singleton { UserControllerImpl() }
        bind() from singleton { UserControllerImpl() }
    }
    var userControllerImpl: UserControllerImpl = kodein.instance()
}