package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.dtos.user.VerifyEmailDto
import com.example.shared.APIResponse
import com.example.shared.RespondsMessages
import com.example.shared.RespondsMessages.MISSING_PARAMETERS
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

    val BASEPATH = "/api/v1"

    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        post("$BASEPATH/user/verify") {
            val receiveParameters = call.receiveParameters()
            val email = receiveParameters["email"]
            val verificationToken = receiveParameters["token"]

            if (
               ! email.isNullOrEmpty()
                && !verificationToken.isNullOrEmpty()
            ) {

                val response = userController.verifyEmail(
                    VerifyEmailDto(
                        userEmail = email,
                        verificationToken = verificationToken
                    )
                )
                call.respond(response)

            }else{
                call.respond(
                    APIResponse<String>(
                        HttpStatusCode.OK.value,MISSING_PARAMETERS, listOf()
                    )
                )
            }
        }
    }
}
