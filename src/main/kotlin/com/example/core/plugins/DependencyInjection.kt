package com.example.core.plugins

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import io.ktor.server.application.*
import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun Application.configureDependencyInjection() {
    var service: UserController = mockk(relaxed = true)
    val di: DI = DI {
        bindSingleton { service }
    }
    val userControllerImpl: UserControllerImpl
    service = UserControllerImpl(di)
}