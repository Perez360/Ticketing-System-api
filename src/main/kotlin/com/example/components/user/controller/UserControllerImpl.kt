package com.example.components.user.controller

import User
import com.example.components.user.dao.UserDao
import com.example.components.user.dao.UserDaoImpl
import com.example.shared.APIResponse
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class UserControllerImpl(/*override val di: DI*/) : UserController /*DIAware*/ {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<UserDao>() with singleton { UserDaoImpl() }
    }
    private val userDao: UserDao = kodein.instance()

    /*: UserDao by instance()*/
    override suspend fun registerUser(user: User): APIResponse<User> {
        val response: APIResponse<User> = try {
            val count=userDao.create(user)
            if (count> 0) {
                user.id=userDao.size()
                APIResponse("USER201", "Created", HttpStatusCode.Created.description, listOf(user))
            } else {
                APIResponse("USER400", "Bad request", HttpStatusCode.BadRequest.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun loginUser(email: String, password: String): APIResponse<User> {
        val response: APIResponse<User> = try {
            var listOfUsers = userDao.list(0, 50)
            if (listOfUsers.isNotEmpty()) {
                APIResponse("USER200", "Success", HttpStatusCode.Accepted.description, listOf())
            } else {
                APIResponse("USER200", "Invalid username or password", HttpStatusCode.NotAcceptable.description, listOf())
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }

        return response
    }

    override suspend fun changeUserPassword(userID: Int, newPassword: String): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                oneUser.password = newPassword
                if (userDao.update(oneUser) > 0) {
                    APIResponse("USER201", "Bad request", HttpStatusCode.Created.description, listOf())
                } else {
                    APIResponse("USER200", "OK", HttpStatusCode.OK.description, listOf())
                }
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun changeUserPhoneNumber(userID: Int, newPhoneNumber: String): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                oneUser.phone = newPhoneNumber
                if (userDao.update(oneUser) > 0) {
                    APIResponse("USER201", "Bad request", HttpStatusCode.Created.description, listOf())
                } else {
                    APIResponse("USER200", "OK", HttpStatusCode.OK.description, listOf())
                }
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun deleteUser(userID: Int): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (userDao.delete(userID) > 0) {
                APIResponse("USER410", "Gone", HttpStatusCode.Gone.description, listOf())
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun findUser(userID: Int): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                APIResponse("USER410", "Found", HttpStatusCode.Found.description, listOf(oneUser))
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun listUsers(startIndex: Int?, size: Int?): APIResponse<User> {
        val start = startIndex ?: 1
        val size = size ?: 50
        val response: APIResponse<User> = try {
            val listUsers = userDao.list(start, size)
            if (listUsers.isNotEmpty()) {
                APIResponse("USER302", "Found", HttpStatusCode.Found.description, listUsers)
            } else {
                APIResponse("PET204", "No Content", HttpStatusCode.NoContent.description, listOf())
            }
        } catch (se: SQLException) {
            log.warn("An error occurred when processing sale", se)
            APIResponse("PET500", "02", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }

    override suspend fun filterUsers(startIndex: Int?, size: Int?, byName: String?): APIResponse<User> {
        val startIndex = startIndex ?: 1
        val numberOfRecords = size ?: 50
        val response = try {
            val filteredUsers = userDao.listByName(startIndex, numberOfRecords, byName)
            if (filteredUsers.isNotEmpty()) {
                APIResponse("PET302", "Found", HttpStatusCode.Found.description, filteredUsers)
            } else {
                APIResponse("PET204", "No Content", HttpStatusCode.NoContent.description, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("PET500", "02", HttpStatusCode.InternalServerError.description, listOf())
        }
        return response
    }
}