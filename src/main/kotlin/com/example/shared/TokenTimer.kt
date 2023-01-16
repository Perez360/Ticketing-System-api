package com.example.shared

import com.example.services.UserRepository
import com.example.services.impl.UserRepositoryImpl
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import java.util.*

object TokenTimer {
    private val tokenTimer = Timer()

    fun startTokenTimer(userEmail: String) {
        val deleteTokenTask = DeleteVerificationTokenHelper(userEmail)
        tokenTimer.schedule(deleteTokenTask, 300000)

    }
    fun stopTokenTimer(){
        tokenTimer.cancel()
    }

}


class DeleteVerificationTokenHelper constructor(private val userEmail: String) : TimerTask() {

    private val kodein = Kodein {
        bind<UserRepository>() with singleton { UserRepositoryImpl() }
    }
    private val userRepository: UserRepository = kodein.instance()
    override fun run() {
        userRepository.deleteVerificationToken(userEmail)
    }


}