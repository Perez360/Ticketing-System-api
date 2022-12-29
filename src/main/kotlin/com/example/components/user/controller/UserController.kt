package com.example.components.user.controller

import User
import com.example.shared.APIResponse

interface UserController {
    suspend fun  registerUser(user: User): APIResponse<User>
    suspend fun changeUserPassword(userID: Int, newPassword: String): APIResponse<User>
    suspend fun changeUserPhoneNumber(userID: Int, newPhoneNumber: String): APIResponse<User>
    suspend fun deleteUser(userID: Int): APIResponse<String>
    suspend fun findUser(userID: Int): APIResponse<User>
    suspend fun listUsers(startIndex: Int?, size: Int?): APIResponse<User>
    suspend fun filterUsers(startIndex: Int? = 1, size: Int? = 50, byName: String?): APIResponse<User>
}