package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.dtos.user.LoginDto
import com.example.shared.APIResponse
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

fun Application.configureLogin() {
    val ROOT_PATH = "/api/v1/account"
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    val userController: UserController = kodein.instance()

    routing {

        get("$ROOT_PATH/account/login") {
            val loginFile = File("./static/html/login.html")
            if (loginFile.exists()) {
                call.respondFile(loginFile)
            } else {
                call.respondText("Login Page not found")
            }
        }

        post("api/v1/account/login") {
            val receiveParameters = call.receiveParameters()
            val email = receiveParameters["email"]
            val password = receiveParameters["password"]

            if (
                !email.isNullOrEmpty()
                && !password.isNullOrEmpty()
            ) {

                val response = userController.loginUser(
                    LoginDto(
                        email = email,
                        password = password
                    )
                )
                call.respond(response)

            } else {
                val invalidParams=APIResponse<String>(
                    HttpStatusCode.NotAcceptable.value, "One or more fields are empty", listOf()
                )
                call.respond(invalidParams)
            }
        }
    }
}