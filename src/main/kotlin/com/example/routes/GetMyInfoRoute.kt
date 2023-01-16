package com.example.routes

import com.example.contollers.UserController
import com.example.contollers.impl.UserControllerImpl
import com.example.security.UserRequestAuthentication
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDashboard(){

    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    var userController: UserController = kodein.instance()
    routing {
        post ("/api/v1/user/get"){
            val receiveParameters = call.receiveParameters()
            val csrf_userid = receiveParameters["csrf_userid"]
            val csrf_token = receiveParameters["csrf_token"]

            if (
                !csrf_userid.isNullOrEmpty() &&
                !csrf_token.isNullOrEmpty()
            ) {
                val authResponse = UserRequestAuthentication.authenticateRequest(
                    csrfUserId = csrf_userid,
                    csrfToken = csrf_token
                )
                if (authResponse.code == HttpStatusCode.OK.value) {
                    val response = userController.getMyInformation(csrf_userid.toInt())
                    call.respond(response)

                } else {
                    call.respond(authResponse)
                }

            }
        }
    }
}