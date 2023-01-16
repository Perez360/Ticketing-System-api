package com.example.security.jwt_auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.FullUserData
import java.time.Instant
import java.util.*

class JWTAuth private constructor(secretkey: String) {
    fun createToken(fullUserData: FullUserData): String? = JWT
        .create()
        .withJWTId("${fullUserData.csrf_userid}")
        .withClaim("email", fullUserData.userEmail)
        .withClaim("first_name", fullUserData.firstName)
        .withClaim("last_name", fullUserData.lastName)
        .withClaim("phone", fullUserData.phone)
        .withClaim("signupDate", fullUserData.signupDate)
        .withIssuedAt(DATE)
        .withSubject(SUBJECT)
        .withIssuer(ISSUER)
        .sign(Algorithm.HMAC256(SECRET_KEY))


    val verifier = JWT
        .require(ALGORITHM)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    companion object {
        lateinit var jwtAuth: JWTAuth
            private set


        private val SECRET_KEY = "secrete"
        private val ISSUER = "com.perezsite.crud"
        private val AUDIENCE = "http://127.0.0.1:1010/requested_page"
        private val REALM = "your realm"
        private val DATE = Date.from(Instant.now())
        private val SUBJECT = "This is a routes token"
        private val ALGORITHM = Algorithm.HMAC256(SECRET_KEY)

        fun initialised(secretkey: String) {
            synchronized(this) {
                if (!this::jwtAuth.isInitialized) {
                    jwtAuth = JWTAuth(secretkey)
                }
            }
        }
    }
}