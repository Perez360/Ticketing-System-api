package com.example.components.user.web

import User
import com.example.components.user.controller.UserControllerImpl
import com.github.salomonbrys.kodein.Kodein
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val kodein = Kodein {
    // bind<UserController>() with singleton { UserControllerImpl(di) }
}
lateinit var userControllerImpl: UserControllerImpl

fun Application.configureRouting() {
    routing {
        post("/api/v1/accounts/register") {
            val user = call.receive<User>()
            val response = userControllerImpl.registerUser(user)

            if (response.httpCode === HttpStatusCode.Created) {
                call.response.headers.apply {
                    this.append("Something1", "value1")
                    this.append("Something2", "value2")
                    this.append("Something3", "value3")
                }
                call.respond(response.data.first())
                call.respond(response.httpCode, response.message)
            } else {
                call.respond(response.httpCode, response.message)
            }
        }



        get("/api/v1/accounts/get/{id}") {
            val userID = call.parameters["id"]!!.toInt()
            val response = userControllerImpl.findUser(userID)
            if (response.httpCode === HttpStatusCode.Found) {
                call.respond(response.data.first())
            } else {
                call.respondText("No user found")
            }
        }



        get("/api/v1/accounts/get-all") {
            val response = userControllerImpl.listUsers(1, 50)
            if (response.httpCode === HttpStatusCode.Found) {
                response.data.map { user ->
                    call.respond(user)
                }
            }
        }

        get("/api/v1/accounts/filter-by") {
            val username = call.request.queryParameters["name"]
            val response = userControllerImpl.filterUsers(1, 50, username)
            if (response.httpCode == HttpStatusCode.Found) {
                response.data.map { user ->
                    call.respond(user)
                }
            }
        }

        delete("/api/v1/accounts/delete/{id}") {
            val id = call.parameters["id"]?.toInt()
            val response = userControllerImpl.deleteUser(id!!)
            if (response.httpCode == HttpStatusCode.Gone) {
                call.respondText("User deleted successfully")
            } else {
                call.respondText("User does not exist")
            }
        }
    }
}

