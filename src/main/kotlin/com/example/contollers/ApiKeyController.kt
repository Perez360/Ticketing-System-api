package com.example.contollers

import com.example.models.APIKey
import com.example.shared.APIResponse

interface ApiKeyController {
    suspend fun addAPIKey(createAPIKeyParams: com.example.dtos.CreateAPIKeyParams): APIResponse<APIKey>
    suspend fun findAPIKeyByID(apiKeyID: String): APIResponse<APIKey>
    suspend fun findAPIKeyByName(apiKeyName: String): APIResponse<APIKey>
    suspend fun deleteAPIKey(apiKeyID: Int): APIResponse<String>
    suspend fun listAllAPIKeys(): APIResponse<APIKey>
}