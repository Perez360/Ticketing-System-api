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

fun Application.configureGetAUser(){
    val kodein = Kodein {
        bind<UserController>() with  singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        get("/api/v1/account/get/{id}") {
            val userID = call.parameters["id"]!!.toInt()
            val response = userController.findUser(userID)
            if (response.httpCode === HttpStatusCode.Found.description) {
                call.respond(response.data.first())
            } else {
                call.respondText("No user found")
            }
        }
    }
}