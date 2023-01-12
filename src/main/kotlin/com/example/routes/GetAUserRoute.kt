//package com.example.components.user.routes
//
//import com.example.components.security.UserRequestAuthentication
//import com.example.contollers.UserController
//import com.example.contollers.impl.UserControllerImpl
//import com.example.shared.APIResponse
//import com.github.salomonbrys.kodein.Kodein
//import com.github.salomonbrys.kodein.bind
//import com.github.salomonbrys.kodein.instance
//import com.github.salomonbrys.kodein.singleton
//import io.ktor.http.*
//import io.ktor.server.application.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*
//
//fun Application.configureGetAUser() {
//    val kodein = Kodein {
//        bind<UserController>() with singleton { UserControllerImpl() }
//    }
//    var userController: UserController = kodein.instance()
//
//    routing {
//        post("/api/v1/account/getForLogin/{id}") {
//            val csrf_userid = call.parameters["csrf_userid"]
//            val csrf_token = call.parameters["csrf_token"]
//            val userID = call.parameters["id"]
//
//            if (
//                !csrf_userid.isNullOrEmpty() &&
//                !csrf_token.isNullOrEmpty() &&
//                !userID.isNullOrEmpty()
//            ) {
//                val authResponse = UserRequestAuthentication.authenticateRequest(
//                    csrf_userid = csrf_userid.toInt(),
//                    csrf_token = csrf_token
//                )
//                if (authResponse.code == HttpStatusCode.OK.value) {
//                    val response = userController.findUser(userID.toInt())
//                    call.respond(response)
//
//                } else {
//                    call.respond(authResponse)
//                }
//            } else {
//                call.respond(
//                    APIResponse<String>(
//                        HttpStatusCode.NotAcceptable.value, "INVALID_INPUT", listOf()
//                    )
//                )
//            }
//        }
//    }
//}