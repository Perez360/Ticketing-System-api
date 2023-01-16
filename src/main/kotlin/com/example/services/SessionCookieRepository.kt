package com.example.services

import com.example.models.SessionCookie

interface SessionCookieRepository  {
    suspend fun createSessionCookie(csrf_userid: Int):SessionCookie?
    suspend fun getSessionCookie(csrf_userid: Int):SessionCookie?
    suspend fun deleteSessionCookie(csrf_userid:Int):Int
    suspend fun updateSessionCookie(csrf_userid:Int):Int
}