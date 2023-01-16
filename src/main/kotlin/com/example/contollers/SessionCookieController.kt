package com.example.contollers


import com.example.models.SessionCookie
import com.example.shared.APIResponse

interface SessionCookieController {
    //    suspend fun registerSessionCookies(csrf_userid: Int): APIResponse<SessionCookie?>
//    suspend fun changeSessionCookies(csrf_userid: Int): APIResponse<SessionCookie?>
//    suspend fun disposeSessionCookies(csrf_userid: Int): APIResponse<String>
    suspend fun checkSession(csrf_userid: Int): APIResponse<SessionCookie?>
}