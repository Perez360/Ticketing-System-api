package com.example.components.user.web

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureLogin() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {


        get("api/v1/account/login"){
            val loginFile = File("./static/login.html")
            if (loginFile.exists()) {
                call.respondFile(loginFile)
            } else {
                call.respondText("Login Page not found")

            }
        }

        post("api/v1/account/login") {
            //val response=userControllerImpl.loginUser()
        }
    }
}