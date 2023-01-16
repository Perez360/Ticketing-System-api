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


fun Application.configureVerifyEmail() {

    val BASEPATH = "/api/v1/user"

    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        post("$BASEPATH/verify") {
            val receiveParameters = call.receiveParameters()
            val email = receiveParameters["email"]!!
            val verificationToken = receiveParameters["token"]!!

            if (
                email.isNotEmpty()
            ) {
                val response1 = userController.sendEmailVerificationToken(
                    userEmail = email,
                )
                if (response1.code == HttpStatusCode.Continue.value) {
                    val response2 = userController.verifyEmail(
                        VerifyEmailDto(
                            userEmail = email,
                            verificationToken = verificationToken
                        )
                    )
                    call.respond(response2)
                }else{
                    call.respond(response1)
                }

            }
        }
    }
}
