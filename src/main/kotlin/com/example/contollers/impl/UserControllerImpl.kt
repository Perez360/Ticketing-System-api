package com.example.contollers.impl

import com.example.contollers.UserController
import com.example.models.*
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

class UserControllerImpl() : UserController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<UserRepository>() with singleton { UserRepositoryImpl() }
    }
    private val userRepository: UserRepository = kodein.instance()

    override suspend fun registerUser(registerUserParams: com.example.dtos.RegisterUserParams): APIResponse<RegisterUserData> {
        val response: APIResponse<RegisterUserData> =
            if (
                !registerUserParams.firstname.isNullOrEmpty()
                && !registerUserParams.lastname.isNullOrEmpty()
                && !registerUserParams.email.isNullOrEmpty()
                && !registerUserParams.phone.isNullOrEmpty()
                && !registerUserParams.password.isNullOrEmpty()

            ) {
                try {
                    if (isEmailExist(registerUserParams.email!!)) {
                        APIResponse(HttpStatusCode.NotAcceptable.value, "Email already exist", listOf())
                    } else {

                        if (registerUserParams.phone?.length == 10) {
                            val oneUser = userRepository.create(registerUserParams)
                            if (oneUser != null) {
                                APIResponse(HttpStatusCode.Created.value, "Successfully registered", listOf(oneUser))
                            } else {
                                APIResponse(HttpStatusCode.NotAcceptable.value, "INVALID_REQUEST", listOf())
                            }
                        } else {
                            APIResponse(
                                HttpStatusCode.BadRequest.value, "Invalid phone number", listOf()
                            )

                        }

                    }
                } catch (sql: SQLException) {
                    log.warn("An error occurred when processing sale", sql)
                    APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NotAcceptable.value, "MISSING_PARAMETERS", listOf())
            }
        return response
    }

//    override suspend fun findUser(email: String): APIResponse<UsersData> {
//        val response: APIResponse<UsersData> = try {
//            val oneUser = userRepository.getForLogin(email)
//            if (oneUser != null) {
//                val data = UsersData(
//                    userId = oneUser.userId,
//                    firstName = oneUser.firstName,
//                    lastName = oneUser.lastName,
//                    email = oneUser.email,
//                    avatar = oneUser.avatar,
//                    phone = oneUser.phone,
//                    dateRegistered = oneUser.dateRegistered,
//                )
//                APIResponse(
//                    HttpStatusCode.OK.value,
//                    "Found a user", listOf(data)
//                )
//            } else {
//                APIResponse(
//                    HttpStatusCode.NoContent.value,
//                    "User does not exist",
//                    listOf()
//                )
//            }
//
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
//        }
//
//        return response
//    }
//
//    override suspend fun findUser(userID: Int): APIResponse<UsersData> {
//        val response: APIResponse<UsersData> = try {
//            val oneUser = userRepository.get(userID)
//            if (oneUser != null) {
//                val data = UsersData(
//                    userId = oneUser.userId,
//                    firstName = oneUser.firstName,
//                    lastName = oneUser.lastName,
//                    email = oneUser.email,
//                    avatar = oneUser.avatar,
//                    phone = oneUser.phone,
//                    dateRegistered = oneUser.dateRegistered,
//                )
//                APIResponse(
//                    HttpStatusCode.OK.value,
//                    "Found a user", listOf(data)
//                )
//            } else {
//                APIResponse(
//                    HttpStatusCode.NoContent.value,
//                    "User does not exist",
//                    listOf()
//                )
//            }
//
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
//        }
//
//        return response
//    }

    override suspend fun changeUserPassword(changePasswordParams: com.example.dtos.ChangePasswordParams): APIResponse<ChangePasswordUserData> {
        val response: APIResponse<ChangePasswordUserData> = try {
            val oneUser = userRepository.get(changePasswordParams.csrf_userid)
            if (oneUser != null) {
                if (oneUser.password != changePasswordParams.newPassword) {
                    if (oneUser.password == changePasswordParams.oldPassword) {
                        if (userRepository.updatePassword(changePasswordParams) > 0) {
                            val updatedUser = ChangePasswordUserData(
                                userId = oneUser.userId,
                                email = oneUser.email,
                                oldPassword = changePasswordParams.oldPassword,
                                newPassword = changePasswordParams.newPassword
                            )
                            APIResponse(
                                HttpStatusCode.OK.value,
                                "Password successfully updated",
                                listOf(updatedUser)
                            )
                        } else {
                            APIResponse(HttpStatusCode.NotAcceptable.value, "INVALID_INPUT", listOf())
                        }
                    } else {
                        APIResponse(
                            HttpStatusCode.InternalServerError.value,
                            "For security reasons, you cannot user old password for new password",
                            listOf()
                        )
                    }
                } else {
                    APIResponse(HttpStatusCode.NoContent.value, "User does not exist", listOf())
                }
            } else {
                APIResponse(
                    HttpStatusCode.InternalServerError.value,
                    "For security reasons, you cannot user old password for new password",
                    listOf()
                )
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun changeUserPhoneNumber(changePhoneParams: com.example.dtos.ChangePhoneParams): APIResponse<ChangePhoneUserData> {
        val response: APIResponse<ChangePhoneUserData> = try {
            val oneUser = userRepository.get(changePhoneParams.csrf_userid)
            if (oneUser != null) {
                oneUser.phone = changePhoneParams.newPhoneNumber
                if (userRepository.updatePhone(changePhoneParams) > 0) {
                    val updatedUser = ChangePhoneUserData(
                        userId = oneUser.userId,
                        email = oneUser.email,
                        oldPhoneNumber = changePhoneParams.oldPhoneNumber,
                        newPhoneNumber = changePhoneParams.newPhoneNumber
                    )
                    APIResponse(
                        HttpStatusCode.OK.value,
                        "Phone number successfully updated",
                        listOf(updatedUser)
                    )
                } else {
                    APIResponse(HttpStatusCode.NotAcceptable.value, "Invalid input", listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "User does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun changeUserEmail(changeEmailParams: com.example.dtos.ChangeEmailParams): APIResponse<ChangeEmailUserData> {
        val response: APIResponse<ChangeEmailUserData> = try {
            val oneUser = userRepository.get(changeEmailParams.csrf_userid)
            if (oneUser != null) {
                if (userRepository.updateEmail(changeEmailParams) > 0) {
                    val updatedUser = ChangeEmailUserData(
                        userId = oneUser.userId,
                        email = oneUser.email,
                        newEmail = oneUser.email
                    )
                    APIResponse(
                        HttpStatusCode.OK.value,
                        "Email successfully changed",
                        listOf(updatedUser)
                    )
                } else {
                    APIResponse(HttpStatusCode.NotAcceptable.value, "Invalid input", listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "User does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun deleteUser(deleteUserParams: com.example.dtos.DeleteUserParams): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (userRepository.delete(deleteUserParams) > 0) {
                APIResponse(
                    HttpStatusCode.Gone.value, "User successfully deleted", listOf()
                )
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "User does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun loginUser(loginParams: com.example.dtos.LoginParams): APIResponse<LoginUserData> {
        val response: APIResponse<LoginUserData> = try {
            val oneUser = userRepository.getForLogin(loginParams.email)
            if (oneUser != null) {
                if (oneUser.password == loginParams.password) {
                    val loginUser = LoginUserData(
                        userId = oneUser.userId,
                        firstName = oneUser.firstName,
                        lastName = oneUser.lastName,
                        email = oneUser.email,
                        avatar = oneUser.avatar,
                        token = oneUser.token,
                    )
                    APIResponse(HttpStatusCode.OK.value, "Login successful", listOf(loginUser))
                } else {
                    APIResponse(HttpStatusCode.NotAcceptable.value, "Invalid Username or password", listOf())
                }
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "User does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun addAvatar(changeAvatarParams: com.example.dtos.ChangeAvatarParams): APIResponse<String> {
        val response: APIResponse<String> = try {
            val count = userRepository.updateAvatar(changeAvatarParams)
            if (count > 0) {
                APIResponse(
                    HttpStatusCode.OK.value,
                    "Avatar successfully changed",
                    listOf()
                )
            } else {
                APIResponse(HttpStatusCode.NotAcceptable.value, "INVALID_INPUT", listOf())
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    private suspend fun isEmailExist(email: String): Boolean = userRepository.get(email) != null


    override suspend fun getAllUsers(): APIResponse<UsersData> {
        val response: APIResponse<UsersData> = try {
            val listOfUsers = userRepository.listAll()
            if (listOfUsers.isNotEmpty()) {
                APIResponse(HttpStatusCode.OK.value, "(${listOfUsers.size}) user(s) found", listOfUsers)
            } else {
                APIResponse(HttpStatusCode.OK.value, "No user found", emptyList())
            }
        } catch (se: SQLException) {
            log.warn("An error occurred when processing sale", se)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun filterUsers(filterUsersParams: com.example.dtos.FilterUsersParams): APIResponse<UsersData> {

        val response: APIResponse<UsersData> = try {
            val filteredUsers = userRepository.listByName(filterUsersParams)
            if (filteredUsers.isNotEmpty()) {
                APIResponse(HttpStatusCode.OK.value, "(${filteredUsers.size}) user(s) found", filteredUsers)
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "No user found", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

//    override suspend fun logoutUser(logoutParams: com.example.dtos.LogoutParams): APIResponse<String> {
//        val response: APIResponse<String> = try {
//            val oneUser = userRepository.get(logoutParams.csrf_userid)
//            if (oneUser != null) {
//                if(userRepository.)
//                APIResponse(HttpStatusCode.OK.value, "Successfully logged out", listOf())
//            } else {
//                APIResponse(HttpStatusCode.NoContent.value, "No Content", listOf())
//            }
//        } catch (sql: SQLException) {
//            log.warn("An error occurred when processing sale", sql)
//            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
//        }
//        return response
//    }


}