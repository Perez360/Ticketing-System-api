package com.example.components.user.dao

import User

interface UserDao {
    suspend fun create(user: User): Int;
    suspend fun get(userID: Int): User?;
    suspend fun update(user: User): Int;
    suspend fun delete(userID: Int): Int;
    suspend fun list(start: Int, size: Int): List<User>;
    suspend fun listByName(startIndex: Int, size: Int, byName:String?): List<User>;
}