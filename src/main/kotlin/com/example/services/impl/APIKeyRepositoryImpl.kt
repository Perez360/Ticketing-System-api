package com.example.services.impl

import com.example.dtos.user.CreateAPIKeyDto
import com.example.models.APIKey
import com.example.security.TokenGenerator
import com.example.services.APIKeyRepository
import com.example.entities.APIKeyTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime

class APIKeyRepositoryImpl : APIKeyRepository {
    override suspend fun create(createAPIKeyDto: CreateAPIKeyDto): Int {
        return transaction {
            APIKeyTable.insert {
                it[name] = createAPIKeyDto.name
                it[description] = createAPIKeyDto.description
                it[token] = TokenGenerator.getToken()
                it[dateCreated] = LocalDateTime.now()
                it[canCreateUsers] = createAPIKeyDto.canCreateUsers
                it[canCreateTickets] = createAPIKeyDto.canCreateTickets
                it[canCheckTickets] = createAPIKeyDto.canCheckTickets
                it[shouldReturnTicketNumber] = createAPIKeyDto.shouldReturnTicketNumber
            }
        }.insertedCount

    }

    override suspend fun get(apikeyID: Int): APIKey? {
        return transaction {
            APIKeyTable.select { APIKeyTable.id eq apikeyID }
                .map {
                    APIKey(
                        id = it[APIKeyTable.id],
                        name = it[APIKeyTable.name],
                        dateCreated = it[APIKeyTable.dateCreated].toString(),
                        description = it[APIKeyTable.description],
                        token = it[APIKeyTable.token],
                        canCreateUsers = it[APIKeyTable.canCreateUsers],
                        canCreateTickets = it[APIKeyTable.canCreateTickets],
                        canCheckTickets = it[APIKeyTable.canCheckTickets],
                        shouldReturnTicketNumber = it[APIKeyTable.shouldReturnTicketNumber]
                    )
                }.singleOrNull()
        }
    }

    override suspend fun get(apiKeyName: String): APIKey? {
        return transaction {
            APIKeyTable.select { APIKeyTable.name eq apiKeyName }
                .map {
                    APIKey(
                        id = it[APIKeyTable.id],
                        name = it[APIKeyTable.name],
                        dateCreated = it[APIKeyTable.dateCreated].toString(),
                        description = it[APIKeyTable.description],
                        token = it[APIKeyTable.token],
                        canCreateUsers = it[APIKeyTable.canCreateUsers],
                        canCreateTickets = it[APIKeyTable.canCreateTickets],
                        canCheckTickets = it[APIKeyTable.canCheckTickets],
                        shouldReturnTicketNumber = it[APIKeyTable.shouldReturnTicketNumber]
                    )
                }.singleOrNull()
        }
    }

    override suspend fun listAll(): List<APIKey> {
        return transaction {
            APIKeyTable.selectAll().map {
                APIKey(
                    id = it[APIKeyTable.id],
                    name = it[APIKeyTable.name],
                    dateCreated = it[APIKeyTable.dateCreated].toString(),
                    description = it[APIKeyTable.description],
                    token = it[APIKeyTable.token],
                    canCreateUsers = it[APIKeyTable.canCreateUsers],
                    canCreateTickets = it[APIKeyTable.canCreateTickets],
                    canCheckTickets = it[APIKeyTable.canCheckTickets],
                    shouldReturnTicketNumber = it[APIKeyTable.shouldReturnTicketNumber]
                )
            }
        }
    }

    override suspend fun delete(apikeyID: Int): Int {
        return transaction {
            APIKeyTable.deleteWhere { APIKeyTable.id eq apikeyID }
        }
    }
}