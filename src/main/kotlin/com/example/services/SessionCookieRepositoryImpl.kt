package com.example.services

import com.example.entities.SessionCookieTable
import com.example.models.SessionCookie
import com.example.security.TokenGenerator
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class SessionCookieRepositoryImpl : SessionCookieRepository {
    override suspend fun createSessionCookie(csrf_userid: Int): SessionCookie? {
        fun rowToSessionCookie(resultRow: ResultRow?): SessionCookie? {

            return if (resultRow == null)
                null
            else {
                SessionCookie(
                    id = resultRow[SessionCookieTable.id],
                    userId = resultRow[SessionCookieTable.user_id],
                    token = resultRow[SessionCookieTable.token],
                    ip = resultRow[SessionCookieTable.ip],
                    creationDate = resultRow[SessionCookieTable.dateCreated].toString(),
                )
            }
        }

        val insertSatament: InsertStatement<Number> = transaction {
            SessionCookieTable.insert {
                it[user_id] = csrf_userid
                it[token] = TokenGenerator.getToken()
            }
        }
        return rowToSessionCookie(insertSatament.resultedValues?.get(0))
    }

    override suspend fun getSessionCookie(csrf_userid: Int): SessionCookie? {
        return transaction {
            SessionCookieTable.select { SessionCookieTable.user_id eq csrf_userid }
                .map {
                    SessionCookie(
                        id = it[SessionCookieTable.id],
                        userId = it[SessionCookieTable.user_id],
                        token = it[SessionCookieTable.token],
                        creationDate = it[SessionCookieTable.dateCreated].toString(),
                        ip = it[SessionCookieTable.ip],
                    )
                }.singleOrNull()
        }
    }

    override suspend fun deleteSessionCookie(csrf_userid: Int): Int {
        return transaction {
            SessionCookieTable.deleteWhere { user_id eq csrf_userid }
        }
    }

    override suspend fun updateSessionCookie(csrf_userid: Int): Int {
        return transaction {
            SessionCookieTable.update({ SessionCookieTable.user_id eq csrf_userid }) {
                it[token] = TokenGenerator.getToken()
                it[dateCreated] = LocalDateTime.now()
            }
        }
    }
}