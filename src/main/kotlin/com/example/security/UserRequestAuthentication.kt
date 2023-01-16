package com.example.security

import com.example.services.SessionCookieRepository
import com.example.services.SessionCookieRepositoryImpl
import com.example.services.UserRepository
import com.example.services.impl.UserRepositoryImpl
import com.example.shared.APIResponse
import com.example.shared.RespondsMessages.INVALID_INPUT
import com.example.shared.RespondsMessages.NO_PERMISSION
import com.example.shared.RespondsMessages.TOKEN_MATCHED
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
        bind<SessionCookieRepository>() with singleton { SessionCookieRepositoryImpl() }
    }
    private val userRepository: UserRepository = kodein.instance()
    private val sessionCookieRepository: SessionCookieRepository = kodein.instance()
    suspend fun authenticateRequest(csrfUserId: String, csrfToken: String): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.getById(csrfUserId.toInt())
            val oneSessionCookie = sessionCookieRepository.getSessionCookie(csrfUserId.toInt())
            if (oneUser != null) {
                if (oneUser.isOnline && oneSessionCookie!!.token == csrfToken) {
                    APIResponse(
                        HttpStatusCode.OK.value,
                        TOKEN_MATCHED,
                        listOf()
                    )
                } else {
                    APIResponse(HttpStatusCode.Forbidden.value, NO_PERMISSION, listOf())
                }
            } else {
                APIResponse(HttpStatusCode.Forbidden.value, NO_PERMISSION, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        } catch (sql: NumberFormatException) {
            APIResponse(HttpStatusCode.NotAcceptable.value, INVALID_INPUT, listOf())
        }
        return response

    }
}