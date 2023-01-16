package com.example.contollers.impl

import com.example.contollers.UserController
import com.example.dtos.user.*
import com.example.models.*
import com.example.services.SessionCookieRepository
import com.example.services.SessionCookieRepositoryImpl
import com.example.services.UserRepository
import com.example.services.impl.UserRepositoryImpl
import com.example.shared.APIResponse
import com.example.shared.Mail.sendMail
import com.example.shared.RespondsMessages.ALREADY_LOGGED_OUT
import com.example.shared.RespondsMessages.EMAIL_ALREADY_VERIFIED
import com.example.shared.RespondsMessages.EMAIL_VERIFY_FAILED
import com.example.shared.RespondsMessages.EMAIL_VERIFY_SUCCESS
import com.example.shared.RespondsMessages.FAILURE_AVATAR
import com.example.shared.RespondsMessages.FAILURE_DELETE_USER
import com.example.shared.RespondsMessages.FAILURE_EMAIL_UPDATE
import com.example.shared.RespondsMessages.FAILURE_LOGOUT
import com.example.shared.RespondsMessages.FAILURE_PASSWORD_UPDATE
import com.example.shared.RespondsMessages.FAILURE_PHONE_UPDATE
import com.example.shared.RespondsMessages.FOUND_USER
import com.example.shared.RespondsMessages.INVALID_OLD_PASSWORD
import com.example.shared.RespondsMessages.INVALID_PHONE
import com.example.shared.RespondsMessages.LOGIN_FAILURE
import com.example.shared.RespondsMessages.LOGIN_SUCCESS
import com.example.shared.RespondsMessages.MISSING_PARAMETERS
import com.example.shared.RespondsMessages.NO_USER_FOUND
import com.example.shared.RespondsMessages.SAME_PASSWORD
import com.example.shared.RespondsMessages.SERVER_ERROR
import com.example.shared.RespondsMessages.SESSION_COOKIE_CHANGED
import com.example.shared.RespondsMessages.SESSION_COOKIE_CHANGED_FAILURE
import com.example.shared.RespondsMessages.SESSION_COOKIE_CREATED
import com.example.shared.RespondsMessages.SESSION_COOKIE_CREATE_FAILURE
import com.example.shared.RespondsMessages.SIGNUP_FAILURE
import com.example.shared.RespondsMessages.SIGNUP_SUCCESS
import com.example.shared.RespondsMessages.SUCCESS_AVATAR
import com.example.shared.RespondsMessages.SUCCESS_DELETE_USER
import com.example.shared.RespondsMessages.SUCCESS_EMAIL_UPDATE
import com.example.shared.RespondsMessages.SUCCESS_LOGOUT
import com.example.shared.RespondsMessages.SUCCESS_PASSWORD_UPDATE
import com.example.shared.RespondsMessages.SUCCESS_PHONE_UPDATE
import com.example.shared.RespondsMessages.TOKEN_SAVE_FAILURE
import com.example.shared.RespondsMessages.TOKEN_SENT_FAILURE
import com.example.shared.RespondsMessages.TOKEN_SENT_SUCCESS
import com.example.shared.RespondsMessages.USER_ALREADY_EXIST
import com.example.shared.RespondsMessages.USER_NOT_EXIST
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class UserControllerImpl : UserController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<UserRepository>() with singleton { UserRepositoryImpl() }
        bind<SessionCookieRepository>() with singleton { SessionCookieRepositoryImpl() }
    }
    private val userRepository: UserRepository = kodein.instance()
    private val sessionCookieRepository: SessionCookieRepository = kodein.instance()

    override suspend fun registerUser(signupUserDto: SignupUserDto): APIResponse<SignupUserData> {
        val response: APIResponse<SignupUserData> =
            if (!signupUserDto.firstname.isNullOrEmpty() && !signupUserDto.lastname.isNullOrEmpty() && !signupUserDto.email.isNullOrEmpty() && !signupUserDto.phone.isNullOrEmpty() && !signupUserDto.password.isNullOrEmpty()

            ) {
                try {
                    if (isEmailExist(signupUserDto.email!!)) {
                        APIResponse(HttpStatusCode.OK.value, USER_ALREADY_EXIST, listOf())
                    } else {
                        if (signupUserDto.phone?.length == 10) {
                            val oneUser = userRepository.createUser(signupUserDto)
                            if (oneUser != null) {
                                APIResponse(HttpStatusCode.Created.value, SIGNUP_SUCCESS, listOf(oneUser))
                            } else {
                                APIResponse(HttpStatusCode.OK.value, SIGNUP_FAILURE, listOf())
                            }
                        } else {
                            APIResponse(
                                HttpStatusCode.OK.value, INVALID_PHONE, listOf()
                            )

                        }

                    }
                } catch (sql: SQLException) {
                    log.warn("An error occurred when processing sale", sql)
                    APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
                }
            } else {
                APIResponse(HttpStatusCode.OK.value, MISSING_PARAMETERS, listOf())
            }
        return response
    }

    override suspend fun verifyEmail(verifyEmailDto: VerifyEmailDto): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.getByEmail(verifyEmailDto.userEmail)
            if (oneUser != null) {
                if (oneUser.verificationToken == verifyEmailDto.verificationToken) {
                    APIResponse(
                        HttpStatusCode.OK.value, EMAIL_VERIFY_SUCCESS, listOf()
                    )
                } else {
                    APIResponse(
                        HttpStatusCode.OK.value, EMAIL_VERIFY_FAILED, listOf()
                    )
                }
            }else{
                APIResponse(
                    HttpStatusCode.OK.value, NO_USER_FOUND, listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response

    }

    override suspend fun sendEmailVerificationToken(userEmail: String): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.getByEmail(userEmail)
            if (oneUser != null) {
                if (!oneUser.isverifield) {
                    val count = userRepository.update(userEmail)
                    if (count > 0) {
                        if (sendMail(oneUser.email, oneUser.firstName) != null) {
                            APIResponse(
                                HttpStatusCode.Continue.value, TOKEN_SENT_SUCCESS, listOf()
                            )
                        } else {
                            APIResponse(
                                HttpStatusCode.OK.value, TOKEN_SENT_FAILURE, listOf()
                            )
                        }
                    } else {
                        APIResponse(
                            HttpStatusCode.OK.value, TOKEN_SAVE_FAILURE, listOf()
                        )
                    }
                } else {
                    APIResponse(
                        HttpStatusCode.OK.value, NO_USER_FOUND, listOf()
                    )
                }
            } else {
                APIResponse(
                    HttpStatusCode.OK.value, EMAIL_ALREADY_VERIFIED, listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response
    }

    override suspend fun sendPasswordRecovery(sendPasswordRecoveryDto: SendPasswordRecoveryDto): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.getByEmail(sendPasswordRecoveryDto.userEmail)
            if (oneUser != null) {
                val count = userRepository.update(sendPasswordRecoveryDto)
                if (count > 0) {
                    sendMail(oneUser.email, oneUser.firstName)
                    APIResponse(
                        HttpStatusCode.OK.value, TOKEN_SENT_SUCCESS, listOf()
                    )
                } else {
                    APIResponse(
                        HttpStatusCode.OK.value, TOKEN_SENT_FAILURE, listOf()
                    )
                }

            } else {
                APIResponse(
                    HttpStatusCode.OK.value, NO_USER_FOUND, listOf()
                )
            }


        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response
    }


    // This is only used by the owner of the account
    override suspend fun findUser(email: String): APIResponse<UsersData> {
        val response: APIResponse<UsersData> = try {
            val oneUser = userRepository.getByEmail(email)
            if (oneUser != null) {
                val data = UsersData(
                    userId = oneUser.csrf_userid,
                    firstName = oneUser.firstName,
                    lastName = oneUser.lastName,
                    email = oneUser.email,
                    avatar = oneUser.avatar,
                    phone = oneUser.phone,
                    isOnline = oneUser.isOnline,
                    signupDate = oneUser.signupDate,
                )
                APIResponse(
                    HttpStatusCode.OK.value, FOUND_USER, listOf(data)
                )
            } else {
                APIResponse(
                    HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response
    }

    override suspend fun findUser(userID: Int): APIResponse<UsersData> {
        val response: APIResponse<UsersData> = try {
            val oneUser = userRepository.getById(userID)
            if (oneUser != null) {
                val data = UsersData(
                    userId = oneUser.csrf_userid,
                    firstName = oneUser.firstName,
                    lastName = oneUser.lastName,
                    email = oneUser.email,
                    avatar = oneUser.avatar,
                    phone = oneUser.phone,
                    isOnline = oneUser.isOnline,
                    signupDate = oneUser.signupDate,
                )
                APIResponse(
                    HttpStatusCode.OK.value, FOUND_USER, listOf(data)
                )
            } else {
                APIResponse(
                    HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response
    }

    override suspend fun getMyInformation(userID: Int): APIResponse<FullUserData> {
        val response: APIResponse<FullUserData> = try {
            val oneUser = userRepository.getById(userID)
            if (oneUser != null) {
                val data = FullUserData(
                    csrf_userid = oneUser.csrf_userid,
                    firstName = oneUser.firstName,
                    lastName = oneUser.lastName,
                    email = oneUser.email,
                    avatar = oneUser.avatar,
                    phone = oneUser.phone,
                    password = oneUser.password,
                    isBan = oneUser.isBan,
                    verificationToken = oneUser.verificationToken,
                    isverifield = oneUser.isverifield,
                    isOnline = oneUser.isOnline,
                    signupDate = oneUser.signupDate,
                    isStaff = oneUser.isStaff,
                    tickets = oneUser.tickets
                )
                APIResponse(
                    HttpStatusCode.OK.value, FOUND_USER, listOf(data)
                )
            } else {
                APIResponse(
                    HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }

        return response
    }

    override suspend fun changeUserPassword(changePasswordParams: EditPasswordDto): APIResponse<ChangePasswordUserData> {
        val response: APIResponse<ChangePasswordUserData> = try {
            val oneUser = userRepository.getById(changePasswordParams.csrf_userid)
            if (oneUser != null) {
                if (oneUser.password != changePasswordParams.newPassword) {
                    if (oneUser.password == changePasswordParams.oldPassword) {
                        if (userRepository.update(changePasswordParams) > 0) {
                            val updatedUser = ChangePasswordUserData(
                                userId = oneUser.csrf_userid,
                                email = oneUser.email,
                                oldPassword = changePasswordParams.oldPassword,
                                newPassword = changePasswordParams.newPassword
                            )
                            APIResponse(
                                HttpStatusCode.OK.value, SUCCESS_PASSWORD_UPDATE, listOf(updatedUser)
                            )
                        } else {
                            APIResponse(HttpStatusCode.OK.value, FAILURE_PASSWORD_UPDATE, listOf())
                        }
                    } else {
                        APIResponse(
                            HttpStatusCode.InternalServerError.value, INVALID_OLD_PASSWORD, listOf()
                        )
                    }
                } else {
                    APIResponse(HttpStatusCode.NoContent.value, SAME_PASSWORD, listOf())
                }
            } else {
                APIResponse(
                    HttpStatusCode.InternalServerError.value, USER_NOT_EXIST, listOf()
                )
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun changeUserPhoneNumber(editPhoneDto: EditPhoneDto): APIResponse<ChangePhoneUserData> {
        val response: APIResponse<ChangePhoneUserData> = try {
            val oneUser = userRepository.getById(editPhoneDto.csrf_userid)
            if (oneUser != null) {
                oneUser.phone = editPhoneDto.newPhoneNumber
                if (userRepository.update(editPhoneDto) > 0) {
                    val updatedUser = ChangePhoneUserData(
                        userId = oneUser.csrf_userid,
                        email = oneUser.email,
                        oldPhoneNumber = editPhoneDto.oldPhoneNumber,
                        newPhoneNumber = editPhoneDto.newPhoneNumber
                    )
                    APIResponse(
                        HttpStatusCode.OK.value, SUCCESS_PHONE_UPDATE, listOf(updatedUser)
                    )
                } else {
                    APIResponse(HttpStatusCode.OK.value, FAILURE_PHONE_UPDATE, listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun changeUserEmail(editEmailDto: EditEmailDto): APIResponse<ChangeEmailUserData> {
        val response: APIResponse<ChangeEmailUserData> = try {
            val oneUser = userRepository.getById(editEmailDto.csrf_userid)
            if (oneUser != null) {
                if (userRepository.update(editEmailDto) > 0) {
                    val updatedUser = ChangeEmailUserData(
                        userId = oneUser.csrf_userid, email = oneUser.email, newEmail = oneUser.email
                    )
                    APIResponse(
                        HttpStatusCode.OK.value, SUCCESS_EMAIL_UPDATE, listOf(updatedUser)
                    )
                } else {
                    APIResponse(HttpStatusCode.OK.value, FAILURE_EMAIL_UPDATE, listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun deleteUser(deleteUserDto: DeleteUserDto): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (userRepository.delete(deleteUserDto) > 0) {
                APIResponse(
                    HttpStatusCode.Gone.value, SUCCESS_DELETE_USER, listOf()
                )
            } else {
                APIResponse(HttpStatusCode.OK.value, FAILURE_DELETE_USER, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun loginUser(loginDto: LoginDto): APIResponse<LoginUserData> {
        val response: APIResponse<LoginUserData> = try {
            val oneUser = userRepository.getByEmail(loginDto.email)
            if (oneUser != null) {
                val oneSessionCookie = sessionCookieRepository.getSessionCookie(oneUser.csrf_userid)
                // Check is user session already active
                if (oneSessionCookie != null) {
                    val count = sessionCookieRepository.updateSessionCookie(oneUser.csrf_userid)
                    if (count > 0) {
                        val newSessionCookie = sessionCookieRepository.getSessionCookie(oneUser.csrf_userid)
                        val loginUser = LoginUserData(
                            userId = oneUser.csrf_userid,
                            firstName = oneUser.firstName,
                            lastName = oneUser.lastName,
                            email = oneUser.email,
                            avatar = oneUser.avatar,
                            token = newSessionCookie!!.token,
                        )
                        APIResponse(
                            HttpStatusCode.OK.value, "$LOGIN_SUCCESS & $SESSION_COOKIE_CHANGED", listOf(loginUser)
                        )
                    } else {
                        APIResponse(
                            HttpStatusCode.OK.value, "$LOGIN_FAILURE & $SESSION_COOKIE_CHANGED_FAILURE", listOf()
                        )
                    }
                } else {
                    //when session token is not available for the user, crate a new session cookie
                    val newSessionCookie = sessionCookieRepository.createSessionCookie(oneUser.csrf_userid)
                    if (newSessionCookie != null) {
                        val loginUser = LoginUserData(
                            userId = oneUser.csrf_userid,
                            firstName = oneUser.firstName,
                            lastName = oneUser.lastName,
                            email = oneUser.email,
                            avatar = oneUser.avatar,
                            token = newSessionCookie.token,
                        )
                        APIResponse(
                            HttpStatusCode.OK.value, "$LOGIN_SUCCESS & $SESSION_COOKIE_CREATED", listOf(loginUser)
                        )
                    } else {
                        APIResponse(
                            HttpStatusCode.OK.value, "$LOGIN_FAILURE & $SESSION_COOKIE_CREATE_FAILURE", listOf()
                        )
                    }
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, USER_NOT_EXIST, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun addAvatar(changeAvatarDto: ChangeAvatarDto): APIResponse<String> {
        val response: APIResponse<String> = try {
            val count = userRepository.update(changeAvatarDto)
            if (count > 0) {
                APIResponse(
                    HttpStatusCode.OK.value, SUCCESS_AVATAR, listOf()
                )
            } else {
                APIResponse(HttpStatusCode.OK.value, FAILURE_AVATAR, listOf())
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    private suspend fun isEmailExist(email: String): Boolean = userRepository.getByEmail(email) != null


    override suspend fun getAllUsers(): APIResponse<UsersData> {
        val response: APIResponse<UsersData> = try {
            val listOfUsers = userRepository.listAll()
            if (listOfUsers.isNotEmpty()) {
                APIResponse(HttpStatusCode.OK.value, "(${listOfUsers.size}) user(s) found", listOfUsers)
            } else {
                APIResponse(HttpStatusCode.OK.value, NO_USER_FOUND, emptyList())
            }
        } catch (se: SQLException) {
            log.warn("An error occurred when processing sale", se)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun filterUsers(filterUsersDto: FilterUsersDto): APIResponse<UsersData> {

        val response: APIResponse<UsersData> = try {
            val filteredUsers = userRepository.filterByName(filterUsersDto)
            if (filteredUsers.isNotEmpty()) {
                APIResponse(HttpStatusCode.OK.value, "(${filteredUsers.size}) user(s) found", filteredUsers)
            } else {
                APIResponse(HttpStatusCode.NoContent.value, NO_USER_FOUND, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }

    override suspend fun logoutUser(logoutDto: LogoutDto): APIResponse<String> {
        val response: APIResponse<String> = try {
            val oneUser = userRepository.getById(logoutDto.csrf_userid)
            if (oneUser != null) {
                if (userRepository.update(logoutDto) > 0) {
                    val count = sessionCookieRepository.deleteSessionCookie(oneUser.csrf_userid)
                    if (count > 0) {
                        APIResponse(HttpStatusCode.OK.value, SUCCESS_LOGOUT, listOf())
                    } else {
                        APIResponse(HttpStatusCode.OK.value, ALREADY_LOGGED_OUT, listOf())
                    }
                } else {
                    APIResponse(HttpStatusCode.OK.value, FAILURE_LOGOUT, listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, NO_USER_FOUND, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, SERVER_ERROR, listOf())
        }
        return response
    }


}