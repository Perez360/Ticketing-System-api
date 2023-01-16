package com.example.components.user.routes


import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.security.UserRequestAuthentication
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


fun Application.configureDeleteUser() {
    val ROOT_PATH = "/api/v1/account"
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userControllerImpl: UserController = kodein.instance()

    routing {

        post("/delete/{id}") {
            val receiveParameters = call.receiveParameters()

            val csrf_userid = receiveParameters["csrf_userid"]
            val csrf_token = receiveParameters["csrf_token"]
            val userID = receiveParameters["id"]


            if (
                !csrf_userid.isNullOrEmpty() &&
                !csrf_token.isNullOrEmpty() &&
                !userID.isNullOrEmpty()
            ) {

                val authResponse = UserRequestAuthentication.authenticateRequest(
                    csrfUserId = csrf_userid,
                    csrfToken = csrf_token
                )
                if (authResponse.code == HttpStatusCode.OK.value) {
//                    val response = userControllerImpl.deleteUser(userID)
//                    call.respond(response)
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