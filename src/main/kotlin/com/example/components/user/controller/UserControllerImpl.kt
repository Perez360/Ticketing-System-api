package com.example.components.user.controller

import User
import com.example.components.user.dao.UserDao
import com.example.shared.APIResponse
import io.ktor.http.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class UserControllerImpl(override val di: DI) : UserController, DIAware {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val userDao: UserDao by instance()
    override suspend fun registerUser(user: User): APIResponse<User> {
        val response: APIResponse<User> = try {
            if (userDao.create(user) > 0) {
                APIResponse("USER201", "Created", HttpStatusCode.Created, listOf(user))
            } else {
                APIResponse("USER400", "Bad request", HttpStatusCode.BadRequest, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError, listOf())
        }
        return response
    }

    override suspend fun changeUserPassword(userID: Int, newPassword: String): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                oneUser.password = newPassword
                if (userDao.update(oneUser) > 0) {
                    APIResponse("USER201", "Bad request", HttpStatusCode.Created, listOf())
                } else {
                    APIResponse("USER200", "OK", HttpStatusCode.OK, listOf())
                }
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError, listOf())
        }
        return response
    }

    override suspend fun changeUserPhoneNumber(userID: Int, newPhoneNumber: String): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                oneUser.phone = newPhoneNumber
                if (userDao.update(oneUser) > 0) {
                    APIResponse("USER201", "Bad request", HttpStatusCode.Created, listOf())
                } else {
                    APIResponse("USER200", "OK", HttpStatusCode.OK, listOf())
                }
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError, listOf())
        }
        return response
    }

    override suspend fun deleteUser(userID: Int): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (userDao.delete(userID) > 0) {
                APIResponse("USER410", "Gone", HttpStatusCode.Gone, listOf())
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError, listOf())
        }
        return response
    }

    override suspend fun findUser(userID: Int): APIResponse<User> {
        val response: APIResponse<User> = try {
            val oneUser = userDao.get(userID)
            if (oneUser != null) {
                APIResponse("USER410", "Found", HttpStatusCode.Found, listOf(oneUser))
            } else {
                APIResponse("USER404", "Not Found", HttpStatusCode.NotFound, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("USER500", "Internal Server Error", HttpStatusCode.InternalServerError, listOf())
        }
        return response
    }

    override suspend fun listUsers(startIndex: Int?, size: Int?): APIResponse<User> {
        val start = startIndex ?: 1
        val size = size ?: 50
        val response: APIResponse<User> = try {
            val listUsers = userDao.list(start, size)
            if (listUsers.isNotEmpty()) {
                APIResponse("USER302", "Found", HttpStatusCode.Found, listUsers)
            } else {
                APIResponse("PET204", "No Content", HttpStatusCode.NoContent, listOf())
            }
        } catch (se: SQLException) {
            log.warn("An error occurred when processing sale", se)
            APIResponse("PET500", "02",HttpStatusCode.InternalServerError , listOf())
        }
        return response
    }

    override suspend fun filterUsers(startIndex: Int?, size: Int?, byName: String?): APIResponse<User> {
        val startIndex = startIndex ?: 1
        val numberOfRecords = size ?: 50
        val response = try {
            val filteredUsers = userDao.listByName(startIndex, numberOfRecords, byName)
            if (filteredUsers.isNotEmpty()) {
                APIResponse("PET302", "Found", HttpStatusCode.Found, filteredUsers)
            } else {
                APIResponse("PET204", "No Content",  HttpStatusCode.NoContent, listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse("PET500", "02",HttpStatusCode.InternalServerError , listOf())
        }
        return response
    }
}