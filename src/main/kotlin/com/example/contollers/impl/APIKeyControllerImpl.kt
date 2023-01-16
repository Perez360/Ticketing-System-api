package com.example.contollers.impl

import com.example.services.APIKeyRepository
import com.example.models.APIKey
import com.example.contollers.ApiKeyController
import com.example.dtos.user.CreateAPIKeyDto
import com.example.shared.APIResponse
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class APIKeyControllerImpl : ApiKeyController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<ApiKeyController>() with singleton { APIKeyControllerImpl() }
    }
    private val apikeyRepository: APIKeyRepository = kodein.instance()
    override suspend fun addAPIKey(createAPIKeyDto: CreateAPIKeyDto): APIResponse<APIKey> {

        val response: APIResponse<APIKey> = try {
            val count = apikeyRepository.create(createAPIKeyDto)
            if (count > 0) {
                val apiKey = APIKey(
                    name = createAPIKeyDto.name,
                    description = createAPIKeyDto.description,
                    dateCreated = createAPIKeyDto.dateCreated,
                    canCreateUsers = createAPIKeyDto.canCreateUsers,
                    canCreateTickets = createAPIKeyDto.canCreateTickets,
                    canCheckTickets = createAPIKeyDto.canCheckTickets,
                    shouldReturnTicketNumber = createAPIKeyDto.shouldReturnTicketNumber,
                )
                APIResponse(HttpStatusCode.Created.value, "Successfully created", listOf(apiKey))
            } else {
                APIResponse(HttpStatusCode.NotAcceptable.value, "Invalid input", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun findAPIKeyByID(apiKeyID: String): APIResponse<APIKey> {
        val response: APIResponse<APIKey> = try {
            val oneAPIKey = apikeyRepository.get(apiKeyID)
            if (oneAPIKey != null) {
                APIResponse(
                    HttpStatusCode.OK.value,
                    "Found a apikey", listOf(oneAPIKey)
                )
            } else {
                APIResponse(
                    HttpStatusCode.NoContent.value,
                    "APIKey does not exist",
                    listOf()
                )
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun findAPIKeyByName(apiKeyName: String): APIResponse<APIKey> {
        val response: APIResponse<APIKey> = try {
            val oneAPIKey = apikeyRepository.get(apiKeyName)
            if (oneAPIKey != null) {
                APIResponse(
                    HttpStatusCode.OK.value,
                    "Found an Apikey", listOf(oneAPIKey)
                )
            } else {
                APIResponse(
                    HttpStatusCode.NoContent.value,
                    "ApiKey does not exist",
                    listOf()
                )
            }

        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }

        return response
    }

    override suspend fun deleteAPIKey(apiKeyID: Int): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (apikeyRepository.delete(apiKeyID) > 0) {
                APIResponse(
                    HttpStatusCode.Gone.value, "APIKey successfully deleted", listOf()
                )
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "APIKey does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun listAllAPIKeys(): APIResponse<APIKey> {
        val response: APIResponse<APIKey> = try {
            val listOfAPIKey = apikeyRepository.listAll()
            if (listOfAPIKey.isNotEmpty()) {
                APIResponse(HttpStatusCode.OK.value, "Found apikeys", listOfAPIKey)
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "No apikey found", listOf())
            }
        } catch (se: SQLException) {
            log.warn("An error occurred when processing sale", se)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }
}