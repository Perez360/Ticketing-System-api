package com.example.components.user.web

import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureGetAllUsers(){
    val kodein = Kodein {
        bind<UserController>() with  singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        get("/api/v1/account/get-all") {
            val response = userController.listUsers(1, 50)
            if (response.httpCode === HttpStatusCode.Found.description) {
                response.data.map { user ->
                    call.respond(user)
                }
            }
        }
    }
}