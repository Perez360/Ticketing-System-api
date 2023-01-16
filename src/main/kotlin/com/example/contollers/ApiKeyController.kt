package com.example.contollers

import com.example.dtos.user.CreateAPIKeyDto
import com.example.models.APIKey
import com.example.shared.APIResponse

interface ApiKeyController {
    suspend fun addAPIKey(createAPIKeyDto: CreateAPIKeyDto): APIResponse<APIKey>
    suspend fun findAPIKeyByID(apiKeyID: String): APIResponse<APIKey>
    suspend fun findAPIKeyByName(apiKeyName: String): APIResponse<APIKey>
    suspend fun deleteAPIKey(apiKeyID: Int): APIResponse<String>
    suspend fun listAllAPIKeys(): APIResponse<APIKey>
}