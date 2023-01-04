package com.example.components.user.web

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.example.core.plugins.configureDependencyInjection
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.server.application.*


fun Application.configureRouting() {
    configureRegister()
    configureLogin()
    configureDashboard()
    configureDeleteUser()
    configureFilterBy()
    configureGetAllUsers()
    configureGetAUser()
}

