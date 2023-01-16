package com.example.services

import com.example.dtos.user.CreateAPIKeyDto
import com.example.models.APIKey

interface APIKeyRepository {

    suspend fun create(createAPIKeyDto: CreateAPIKeyDto): Int
    suspend fun get(apikeyID: Int): APIKey?
    suspend fun get(apiKeyName: String): APIKey?
    suspend fun listAll(): List<APIKey>
    suspend fun delete(apikeyID: Int): Int

}