package com.example.routes

import com.example.security.UserRequestAuthentication
import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
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

fun Application.configureAvatar(){
    val ROOT_PATH = "/api/v1/account"

    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()

    routing {
        post ("$ROOT_PATH/add-avatar"){
                val receiveParameters = call.receiveParameters()
                val csrf_userid = receiveParameters["csrf_userid"]
                val csrf_token = receiveParameters["csrf_token"]
                val newAvatar = receiveParameters["avatar"]


                if (
                    !csrf_userid.isNullOrEmpty() &&
                    !csrf_token.isNullOrEmpty() &&
                    !newAvatar.isNullOrEmpty()
                ) {

                    val authResponse = UserRequestAuthentication.authenticateRequest(
                        csrf_userid = csrf_userid,
                        csrf_token = csrf_token

                    )
                    if (authResponse.code == HttpStatusCode.OK.value) {
                        val response = userController.addAvatar(
                            com.example.dtos.ChangeAvatarParams(
                                csrf_userid = csrf_userid.toInt(),
                                avatar = newAvatar
                            )
                        )
                        call.respond(response)

                    } else {
                        call.respond(authResponse)
                    }
                } else {
                    call.respond(
                        APIResponse<String>(
                            HttpStatusCode.BadRequest.value, "INVALID_INPUT", emptyList()
                        )
                    )
                }
            }
        }

}