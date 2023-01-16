package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SignupUserData(
    var userId: Int,
    var email: String,
    var isVerified: Boolean,
    var dateRegistered: String,
)

@Serializable
data class UsersData(
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var email: String,
    var isOnline: Boolean,
    var avatar: String?,
    var signupDate: String,
)

@Serializable
data class LoginUserData(
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var avatar: String?,
    var token: String?,
)

@Serializable
data class FullUserData(
    var csrf_userid: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var avatar: String?,
    var phone: String,
    var password: String,
    var isBan: Boolean,
    var verificationToken: String?,
    var isverifield: Boolean,
    var tickets:Int,
    var isOnline: Boolean,
    var signupDate: String,
    var isStaff: Boolean,
)

@Serializable
data class ChangePasswordUserData(
    var userId: Int,
    var email: String,
    var oldPassword: String,
    var newPassword: String,
)

@Serializable
data class ChangeEmailUserData(
    var userId: Int,
    var email: String,
    var newEmail: String,
)

@Serializable
data class ChangeAvatarUserData(
    var userId: Int,
    var email: String,
    var newAvatar: String,
)

@Serializable
data class ChangePhoneUserData(
    var userId: Int,
    var email: String,
    var oldPhoneNumber: String,
    var newPhoneNumber: String,
)