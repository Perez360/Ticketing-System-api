package com.example.contollers

import com.example.models.*
import com.example.shared.APIResponse

interface UserController {
    suspend fun registerUser(registerUserParams: com.example.dtos.RegisterUserParams): APIResponse<RegisterUserData>
    suspend fun loginUser(loginParams: com.example.dtos.LoginParams): APIResponse<LoginUserData>
    suspend fun addAvatar(changeAvatarParams: com.example.dtos.ChangeAvatarParams): APIResponse<String>

    //    suspend fun findUser(email: String): APIResponse<UsersData>
//    suspend fun findUser(userID: Int): APIResponse<UsersData>
    suspend fun changeUserPassword(changePasswordParams: com.example.dtos.ChangePasswordParams): APIResponse<ChangePasswordUserData>
    suspend fun changeUserPhoneNumber(changePhoneParams: com.example.dtos.ChangePhoneParams): APIResponse<ChangePhoneUserData>
    suspend fun changeUserEmail(changeEmailParams: com.example.dtos.ChangeEmailParams): APIResponse<ChangeEmailUserData>
    suspend fun deleteUser(deleteUserParams: com.example.dtos.DeleteUserParams): APIResponse<String>
    suspend fun getAllUsers(): APIResponse<UsersData>
    suspend fun filterUsers(filterUsersParams: com.example.dtos.FilterUsersParams): APIResponse<UsersData>
//    suspend fun logoutUser(logoutParams: com.example.dtos.LogoutParams): APIResponse<String>
}