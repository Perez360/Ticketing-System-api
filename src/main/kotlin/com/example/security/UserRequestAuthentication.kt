package com.example.security

import com.example.services.UserRepository
import com.example.services.impl.UserRepositoryImpl
import com.example.shared.APIResponse
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException


object UserRequestAuthentication {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<UserRepository>() with singleton { UserRepositoryImpl() }
    }
    private val userRepository: UserRepository = kodein.instance()
    suspend fun authenticateRequest(csrf_userid: String, csrf_token: String): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.get(csrf_userid.toInt())
            if (oneUser != null) {
                if (oneUser.token.equals(csrf_token)) {
                    APIResponse(
                        HttpStatusCode.OK.value,
                        "Matched token",
                        listOf()
                    )
                } else {
                    APIResponse(HttpStatusCode.Forbidden.value, "NO_PERMISSION", listOf())
                }
            } else {
                APIResponse(HttpStatusCode.Forbidden.value, "NO_PERMISSION", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        } catch (sql: NumberFormatException) {
            APIResponse(HttpStatusCode.BadRequest.value, "INVALID_INPUT", listOf())
        }
        return response

    }
}