package com.example.security

class TokenGenerator {
    companion object {

        fun getToken(): String {
            var token: String = ""
            val chars = ('a'..'c') + ('0'..'9')
            (1..32).map {
                token += chars.random()
            }
            return token.substring(0, 32)
        }

    }
}