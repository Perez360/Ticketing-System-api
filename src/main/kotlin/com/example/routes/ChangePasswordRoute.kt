package com.example.routes

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


fun Application.configureChangePasswordRoute() {
    val kodein = Kodein {
        bind<UserController>() with singleton { UserControllerImpl() }
    }
    val userController: UserController = kodein.instance()



    routing {

        post("api/v1/account/change-password") {
            val formParameters = call.receiveParameters()

            val csrf_userid = formParameters["csrf_userid"]
            val csrf_token = formParameters["csrf_token"]
            val oldPassword = formParameters["oldPassword"]
            val newPassword = formParameters["newPassword"]

            if (
                !csrf_userid.isNullOrEmpty()
                && !csrf_token.isNullOrEmpty()
                && !oldPassword.isNullOrEmpty()
                && !newPassword.isNullOrEmpty()
            ) {

                val changePasswordParams = com.example.dtos.ChangePasswordParams(
                    csrf_userid = csrf_userid.toInt(),
                    csrf_token = csrf_token,
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
                val response = userController.changeUserPassword(changePasswordParams)
                call.respond(response)
            } else {
                call.respond(
                    APIResponse<String>(
                        HttpStatusCode.BadRequest.value, "INVALID_REQUEST", listOf()
                    )
                )
            }


        }
    }

}