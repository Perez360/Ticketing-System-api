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

fun Application.configureDeleteUser() {
    val kodein = Kodein {
        bind<UserController>() with  singleton { UserControllerImpl() }
    }
    var userControllerImpl: UserController = kodein.instance()

    routing {

        delete("/api/v1/account/delete/{id}") {
            val id = call.parameters["id"]?.toInt()
            val response = userControllerImpl.deleteUser(id!!)
            if (response.httpCode == HttpStatusCode.Gone.description) {
                call.respondText("User deleted successfully")
            } else {
                call.respondText("User does not exist")
            }
        }
    }
}