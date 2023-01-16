package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.dtos.user.SignupUserDto
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRegister() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    val userController: UserController = kodein.instance()


    routing {
        get("/api/v1/account/register") {
            val registerFile = File("./static/html/register.html")
            if (registerFile.exists()) {
                call.respondFile(registerFile)
            } else {
                call.respondText("Register Page not found")
            }
        }


        post("/api/v1/account/register") {
            val formParameters = call.receiveParameters()

            val firstname = formParameters["firstname"]
            val lastname = formParameters["lastname"]
            val email = formParameters["email"]
            val phone = formParameters["phone"]
            val password = formParameters["password"]

            val response = userController.registerUser(
                SignupUserDto(
                    firstname = firstname,
                    lastname = lastname,
                    email = email,
                    phone = phone,
                    password = password
                )
            )
            call.respond(response)


        }
    }
}

