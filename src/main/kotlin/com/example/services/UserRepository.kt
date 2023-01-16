package com.example.services

import com.example.dtos.user.*
import com.example.models.FullUserData
import com.example.models.SignupUserData
import com.example.models.UsersData

interface UserRepository {
    suspend fun createUser(signupUserDto: SignupUserDto): SignupUserData?
    suspend fun update(loginDto: LoginDto): Int
    suspend fun getById(userID: Int): FullUserData?
    suspend fun getByEmail(userEmail: String): FullUserData?
    suspend fun update(editPasswordParams: EditPasswordDto): Int
    suspend fun update(editPhoneDto: EditPhoneDto): Int
    suspend fun update(verifyEmailDto: VerifyEmailDto): Int
    suspend fun update(banEmailDto: BanEmailDto): Int
    suspend fun update(sendPasswordRecoveryDto: SendPasswordRecoveryDto): Int
    suspend fun update(editEmailDto: EditEmailDto): Int
    suspend fun update(editAvatarDto: ChangeAvatarDto): Int
    suspend fun update(userEmail: String): Int
    suspend fun update(logoutDto: LogoutDto): Int
     fun deleteVerificationToken(userEmail: String): Int
    suspend fun delete(deleteUserDto: DeleteUserDto): Int
    suspend fun listAll(): List<UsersData>
    suspend fun filterByName(filterUsersDto: FilterUsersDto): List<UsersData>







}