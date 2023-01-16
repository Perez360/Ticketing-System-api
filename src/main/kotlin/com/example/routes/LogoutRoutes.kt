package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.dtos.user.LogoutDto
import com.example.security.UserRequestAuthentication
import com.example.shared.APIResponse
import com.example.shared.RespondsMessages.MISSING_PARAMETERS
import com.example.shared.RespondsMessages.TOKEN_MATCHED
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureLogout() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    val userController: UserController = kodein.instance()

    routing {
        post("api/v1/user/logout") {
            val receiveParameters = call.receiveParameters()
            val csrf_userid = receiveParameters["csrf_userid"]
            val csrf_token = receiveParameters["csrf_token"]

            if (
                !csrf_userid.isNullOrEmpty()
                && !csrf_token.isNullOrEmpty()
            ) {
                val authResponse = UserRequestAuthentication.authenticateRequest(
                    csrfUserId = csrf_userid,
                    csrfToken = csrf_token
                )

                if (authResponse.code == HttpStatusCode.OK.value) {
                    val response = userController.logoutUser(
                        LogoutDto(
                            csrf_userid = csrf_userid.toInt(),
                            csrf_token = csrf_token
                        )
                    )
                    call.respond(response)
                } else {
                    call.respond(authResponse)
                }
            } else {

                val invalidParams = APIResponse<String>(
                    HttpStatusCode.OK.value, MISSING_PARAMETERS, listOf()
                )
                call.respond(invalidParams)
            }
        }

    }
}

