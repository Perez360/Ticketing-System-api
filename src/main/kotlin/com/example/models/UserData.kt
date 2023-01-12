package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserData(
    var userId: Int,
    var email: String,
    var status: Int,
    var dateRegistered: String
)

@Serializable
data class UsersData(
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var email: String,
    var status: Int,
    var isBan:  Int,
    var avatar:String?,
    var dateRegistered: String
)

@Serializable
data class LoginUserData(
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var avatar:String?,
    var token: String?,
)

@Serializable
data class FullUserData(
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var avatar:String?,
    var phone: String,
    var password: String,
    var isBan: Int,
    var status: Int ,
    var token:String?,
    var dateRegistered: String
)

@Serializable
data class ChangePasswordUserData(
    var userId: Int,
    var email: String,
    var oldPassword: String,
    var newPassword: String
)
@Serializable
data class ChangeEmailUserData(
    var userId: Int ,
    var email: String,
    var newEmail: String
)

@Serializable
data class ChangeAvatarUserData(
    var userId: Int ,
    var email: String,
    var newAvatar:String
)
@Serializable
data class ChangePhoneUserData(
    var userId: Int,
    var email: String,
    var oldPhoneNumber: String,
    var newPhoneNumber: String
)