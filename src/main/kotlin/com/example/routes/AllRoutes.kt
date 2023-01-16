package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        static("/static") {
            resources("webfiles")
        }
    }
    configureRegister()
    configureLogin()
    configureVerifyEmail()
    configureSendEmailVerificationToken()
    configureChangePasswordRoute()
    configureDashboard()
    configureAvatar()
    configureChangeEmail()
    configureFilterBy()
    configureGetAllUsers()
    configureForgotPassword()
//    configureGetAUser()
    //configureAPIKey()
    configureLogout()
}

