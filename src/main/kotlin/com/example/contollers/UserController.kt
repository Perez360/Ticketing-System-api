package com.example.contollers

import com.example.dtos.user.*
import com.example.models.*
import com.example.shared.APIResponse

interface UserController {
    suspend fun registerUser(signupUserDto: SignupUserDto): APIResponse<SignupUserData>
    suspend fun verifyEmail(verifyEmailDto: VerifyEmailDto): APIResponse<String>
    suspend fun sendEmailVerificationToken(userEmail:String): APIResponse<String>
    suspend fun sendPasswordRecovery(sendPasswordRecoveryDto: SendPasswordRecoveryDto): APIResponse<String>
    suspend fun loginUser(loginDto: LoginDto): APIResponse<LoginUserData>
    suspend fun addAvatar(changeAvatarDto: ChangeAvatarDto): APIResponse<String>

    suspend fun findUser(email: String): APIResponse<UsersData>
    suspend fun findUser(userID: Int): APIResponse<UsersData>
    suspend fun getMyInformation(userID: Int): APIResponse<FullUserData>
    suspend fun changeUserPassword(changePasswordParams: EditPasswordDto): APIResponse<ChangePasswordUserData>
    suspend fun changeUserPhoneNumber(editPhoneDto: EditPhoneDto): APIResponse<ChangePhoneUserData>
    suspend fun changeUserEmail(editEmailDto: EditEmailDto): APIResponse<ChangeEmailUserData>
    suspend fun deleteUser(deleteUserDto: DeleteUserDto): APIResponse<String>
    suspend fun getAllUsers(): APIResponse<UsersData>
    suspend fun filterUsers(filterUsersDto: FilterUsersDto): APIResponse<UsersData>
    suspend fun logoutUser(logoutParams: LogoutDto): APIResponse<String>
}