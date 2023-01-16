package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.dtos.user.VerifyEmailDto
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureSendEmailVerificationToken() {

    val BASEPATH = "/api/v1"

    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        post("$BASEPATH/user/send-verification-token") {
            val receiveParameters = call.receiveParameters()
            val email = receiveParameters["email"]

            if (
                !email.isNullOrEmpty()
            ) {
                val response = userController.sendEmailVerificationToken(
                    userEmail = email,
                )
                call.respond(response)
            }
        }
    }
}
