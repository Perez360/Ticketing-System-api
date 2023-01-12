package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureDashboard(){
    routing {
        get ("/api/v1/dashboard"){
            val dashboardFile=File("./static/html/dashboard.html")
            if (dashboardFile.exists()) {
                call.respondFile(dashboardFile)
            }else{
                call.respondText("Dashboard page dos not exist")
            }
        }
    }
}