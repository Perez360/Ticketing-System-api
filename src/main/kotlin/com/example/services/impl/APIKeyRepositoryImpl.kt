package com.example.services.impl

import com.example.dtos.CreateAPIKeyParams
import com.example.models.APIKey
import com.example.security.TokenGenerator
import com.example.services.APIKeyRepository
import com.example.tables.APIKeyTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class APIKeyRepositoryImpl : APIKeyRepository {
    override suspend fun create(createAPIKeyParams: CreateAPIKeyParams): Int {
        return transaction {
            APIKeyTable.insert {
                it[name] = createAPIKeyParams.name
                it[description] = createAPIKeyParams.description
                it[authorName] = createAPIKeyParams.authorName
                it[token] = TokenGenerator.getToken()
                it[dateCreated] = LocalDate.now().toString()
                it[canCreateUsers] = if (createAPIKeyParams.canCreateUsers) 1 else 0
                it[canCreateTickets] = if (createAPIKeyParams.canCreateTickets) 1 else 0
                it[canCheckTickets] = if (createAPIKeyParams.canCheckTickets) 1 else 0
                it[shouldReturnTicketNumber] = if (createAPIKeyParams.shouldReturnTicketNumber) 1 else 0
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
                        authorName = it[APIKeyTable.authorName],
                        dateCreated = it[APIKeyTable.dateCreated],
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
                        authorName = it[APIKeyTable.authorName],
                        dateCreated = it[APIKeyTable.dateCreated],
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
                    authorName = it[APIKeyTable.authorName],
                    dateCreated = it[APIKeyTable.dateCreated],
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