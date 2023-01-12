package com.example.services

import com.example.models.FullUserData
import com.example.models.RegisterUserData
import com.example.models.UsersData

interface UserRepository {
    suspend fun create(registerUserParams: com.example.dtos.RegisterUserParams): RegisterUserData?;
    suspend fun get(userID: Int): FullUserData?;
    suspend fun getForLogin(userEmail: String): FullUserData?;

    suspend fun get(email: String): FullUserData?;

    suspend fun updatePassword(changePasswordParams: com.example.dtos.ChangePasswordParams): Int;
    suspend fun updatePhone(changePhoneParams: com.example.dtos.ChangePhoneParams): Int;
    suspend fun updateEmail(changeEmailParams: com.example.dtos.ChangeEmailParams): Int;
    suspend fun updateAvatar(changeAvatarParams: com.example.dtos.ChangeAvatarParams): Int;
    suspend fun delete(deleteUserParams: com.example.dtos.DeleteUserParams): Int;
    suspend fun listAll(): List<UsersData>
    suspend fun listByName(filterUsersParams: com.example.dtos.FilterUsersParams): List<UsersData>
}