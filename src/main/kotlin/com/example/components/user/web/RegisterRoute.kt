package com.example.components.user.web

import User
import com.example.components.user.controller.UserController
import com.example.components.user.controller.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRegister() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()


    routing {
        get("/api/v1/account/register") {
            val registerFile = File("./static/register.html")
            if (registerFile.exists()) {
                call.respondFile(registerFile)
            } else {
                call.respondText("Register Page not found")

            }
        }
        post("/api/v1/account/register") {

            val formParameters = call.receiveParameters()
            val firstname = formParameters["firstname"].toString()
            val lastname = formParameters["lastname"].toString()
            val email = formParameters["email"].toString()
            val phone = formParameters["phone"].toString()
            val password = formParameters["password"].toString()

            val user = User(
                id = 100,
                firstname = firstname,
                lastname = lastname,
                email = email,
                phone = phone,
                password = password
            )

            val response = userController.registerUser(user)
            call.respond(response)
        }
    }
}