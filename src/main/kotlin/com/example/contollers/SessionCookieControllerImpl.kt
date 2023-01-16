package com.example.contollers

import com.example.models.SessionCookie
import com.example.services.SessionCookieRepository
import com.example.services.SessionCookieRepositoryImpl
import com.example.shared.APIResponse
import com.example.shared.RespondsMessages.NO_SESSION_COOKIE_FOUND
import com.example.shared.RespondsMessages.SERVER_ERROR
import com.example.shared.RespondsMessages.SESSION_COOKIE_CHANGED
import com.example.shared.RespondsMessages.SESSION_COOKIE_CHANGED_FAILURE
import com.example.shared.RespondsMessages.SESSION_COOKIE_CREATED
import com.example.shared.RespondsMessages.SESSION_COOKIE_CREATE_FAILURE
import com.example.shared.RespondsMessages.SESSION_COOKIE_DELETED
import com.example.shared.RespondsMessages.SESSION_COOKIE_DELETED_FAILURE
import com.example.shared.RespondsMessages.SESSION_COOKIE_FOUND
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class SessionCookieControllerImpl : SessionCookieController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<SessionCookieRepository>() with singleton { SessionCookieRepositoryImpl() }
    }
    private val sessionCookieRepository: SessionCookieRepository = kodein.instance()
//    override suspend fun registerSessionCookies(csrf_userid: Int): APIResponse<SessionCookie?> {
//        val response: APIResponse<SessionCookie?> = try {
//            val oneSessionCookie = sessionCookieRepository.createSessionCookie(csrf_userid)
//            if (oneSessionCookie != null) {
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_CREATED, listOf(oneSessionCookie)
//                )
//            } else {
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_CREATE_FAILURE, listOf()
//                )
//            }
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
//        }
//        return response
//    }
//
//    override suspend fun changeSessionCookies(csrf_userid: Int): APIResponse<SessionCookie?> {
//        val response: APIResponse<SessionCookie?> = try {
//            val count = sessionCookieRepository.updateSessionCookie(csrf_userid)
//            if (count > 1) {
//                val oneSessionCookie = sessionCookieRepository.getSessionCookie(csrf_userid)
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_CHANGED, listOf(oneSessionCookie)
//                )
//            } else {
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_CHANGED_FAILURE, listOf()
//                )
//            }
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
//        }
//        return response
//    }
//
//    override suspend fun disposeSessionCookies(csrf_userid: Int): APIResponse<String> {
//        val response: APIResponse<String> = try {
//            val count = sessionCookieRepository.deleteSessionCookie(csrf_userid)
//            if (count > 1) {
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_DELETED, listOf()
//                )
//            } else {
//                APIResponse(
//                    HttpStatusCode.OK.value, SESSION_COOKIE_DELETED_FAILURE, listOf()
//                )
//            }
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
//        }
//        return response
//    }

    override suspend fun checkSession(csrf_userid: Int): APIResponse<SessionCookie?> {
        val response: APIResponse<SessionCookie?> = try {
            val oneSessionCookie = sessionCookieRepository.getSessionCookie(csrf_userid)
            if (oneSessionCookie != null) {
                APIResponse(
                    HttpStatusCode.OK.value, SESSION_COOKIE_FOUND, listOf(oneSessionCookie)
                )
            } else {
                APIResponse(
                    HttpStatusCode.OK.value, NO_SESSION_COOKIE_FOUND, listOf()
                )
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }
}