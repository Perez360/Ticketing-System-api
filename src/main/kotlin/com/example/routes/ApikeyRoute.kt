package com.example.routes

import com.example.contollers.impl.APIKeyControllerImpl
import com.example.contollers.ApiKeyController
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

fun Application.configureAPIKey() {
    val ROOT_PATH = "/api/v1/apikey"
    val kodein = Kodein {
        bind<ApiKeyController>() with singleton { APIKeyControllerImpl() }
    }
    val apiController: ApiKeyController = kodein.instance()


    routing {
        route(ROOT_PATH) {
            post("/getForLogin-api") {

                val receiveParameters = call.receiveParameters()
                val csrf_userid = receiveParameters["csrf_userid"]
                val csrf_token = receiveParameters["csrf_token"]
                val apikeynameQuery = receiveParameters["name"]


                if (
                    !csrf_userid.isNullOrEmpty() &&
                    !csrf_token.isNullOrEmpty() &&
                    !apikeynameQuery.isNullOrEmpty()
                ) {

                    val authResponse = UserRequestAuthentication.authenticateRequest(
                        csrfUserId = csrf_userid,
                        csrfToken = csrf_token
                    )
                    if (authResponse.code == HttpStatusCode.OK.value) {
                        val response = apiController.findAPIKeyByName(apikeynameQuery)
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
            post("/get-all") {
                val receiveParameters = call.receiveParameters()
                val apikeynameQuery = receiveParameters["name"]
                if (!apikeynameQuery.isNullOrEmpty()) {
                    val response = apiController.listAllAPIKeys()
                    call.respond(response)
                } else {
                    call.respond(
                        APIResponse<String>(
                            HttpStatusCode.BadRequest.value, "INVALID_INPUT", emptyList()
                        )
                    )
                }
            }
            post("/add-api") {
                val receiveParameters = call.receiveParameters()
                val name = receiveParameters["name"]
                val author = receiveParameters["author"]
                val description = receiveParameters["description"]
                val dateCreated = receiveParameters["dateCreated"]
                val canCreateUser = receiveParameters["canCreateUser"]
                val canUpdateUser = receiveParameters["canUpdateUser"]
                val canDeleteUser = receiveParameters["canDeleteUser"]
                call.respondText("hello")
                if (
                    !name.isNullOrEmpty()
                    && !author.isNullOrEmpty()
                    && !description.isNullOrEmpty()
                    && !dateCreated.isNullOrEmpty()
                    && !canCreateUser.isNullOrEmpty()
                    && !canUpdateUser.isNullOrEmpty()
                    && !canDeleteUser.isNullOrEmpty()
                ) {

                } else {
                    call.respond(
                        APIResponse<String>(
                            HttpStatusCode.NotAcceptable.value, "INVALID INPUT", listOf()
                        )
                    )
                }


            }

        }
    }
}