package com.example.services.impl

import com.example.components.dto.*
import com.example.components.security.TokenGenerator
import com.example.tables.UserTable
import com.example.models.FullUserData
import com.example.models.RegisterUserData
import com.example.models.UsersData
import com.example.services.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override suspend fun create(registerUserParams: com.example.dtos.RegisterUserParams): RegisterUserData? {
        val insertStatement: InsertStatement<Number> = transaction {
            UserTable.insert {
                it[firstname] = registerUserParams.firstname!!
                it[lastname] = registerUserParams.lastname!!
                it[lastname] = registerUserParams.lastname!!
                it[email] = registerUserParams.email!!
                it[phone] = registerUserParams.phone!!
                it[isBan] = 0
                it[status] = 0
                it[password] = registerUserParams.password!!
            }
        }
        return rowToUserReg(insertStatement.resultedValues?.get(0))
    }

    private fun rowToUserReg(resultRow: ResultRow?): RegisterUserData? {
        return if (resultRow == null) null else {
            RegisterUserData(
                userId = resultRow[UserTable.id],
                email = resultRow[UserTable.email],
                status = resultRow[UserTable.status],
                dateRegistered = resultRow[UserTable.dateRegistered].toString()
            )
        }
    }

    override suspend fun get(userID: Int): FullUserData? {
        return transaction {
            UserTable.select { UserTable.id eq userID }
                .map {
                    FullUserData(
                        userId = it[UserTable.id],
                        email = it[UserTable.email],
                        avatar = it[UserTable.avatar],
                        firstName = it[UserTable.firstname],
                        lastName = it[UserTable.lastname],
                        phone = it[UserTable.phone],
                        isBan = it[UserTable.isBan],
                        status = it[UserTable.status],
                        token = it[UserTable.token],
                        password = it[UserTable.password],
                        dateRegistered = it[UserTable.dateRegistered].toString()
                    )
                }.singleOrNull()
        }

    }

    override suspend fun get(email: String): FullUserData? {
        return transaction {
            UserTable.select { UserTable.email eq email }
                .map {
                    FullUserData(
                        userId = it[UserTable.id],
                        email = it[UserTable.email],
                        avatar = it[UserTable.avatar],
                        firstName = it[UserTable.firstname],
                        lastName = it[UserTable.lastname],
                        phone = it[UserTable.phone],
                        isBan = it[UserTable.isBan],
                        status = it[UserTable.status],
                        token = it[UserTable.token],
                        password = it[UserTable.password],
                        dateRegistered = it[UserTable.dateRegistered].toString()
                    )
                }.singleOrNull()

        }
    }

    override suspend fun getForLogin(userEmail: String): FullUserData? {
        fun updateToken() {
            transaction {
                UserTable.update({ UserTable.email eq userEmail }) {
                    it[status]=1
                    it[token] = TokenGenerator.getToken()
                }
            }
        }

        return transaction {
            val query = UserTable.select(UserTable.email eq userEmail)
            updateToken()
            query.map {
                FullUserData(
                    userId = it[UserTable.id],
                    email = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    phone = it[UserTable.phone],
                    isBan = it[UserTable.isBan],
                    status = it[UserTable.status],
                    token = it[UserTable.token],
                    password = it[UserTable.password],
                    dateRegistered = it[UserTable.dateRegistered].toString()
                )
            }.singleOrNull()

        }
    }


    override suspend fun delete(deleteUserParams: com.example.dtos.DeleteUserParams): Int {
        return transaction {
            UserTable.deleteWhere { id eq deleteUserParams.userID }
        }
    }

    override suspend fun updatePassword(changePasswordParams: com.example.dtos.ChangePasswordParams): Int {
        return transaction {
            UserTable.update({ UserTable.id eq changePasswordParams.csrf_userid }) {
                it[password] = changePasswordParams.newPassword
            }
        }

    }

    override suspend fun updatePhone(changePhoneParams: com.example.dtos.ChangePhoneParams): Int {
        return transaction {
            UserTable.update({ UserTable.id eq changePhoneParams.csrf_userid }) {
                it[password] = changePhoneParams.newPhoneNumber
            }
        }
    }

    override suspend fun updateEmail(changeEmailParams: com.example.dtos.ChangeEmailParams): Int {
        return transaction {
            UserTable.update({ UserTable.id eq changeEmailParams.csrf_userid }) {
                it[email] = changeEmailParams.newEmail
            }
        }
    }

    override suspend fun updateAvatar(changeAvatarParams: com.example.dtos.ChangeAvatarParams): Int {
        return transaction {
            UserTable.update({ UserTable.id eq changeAvatarParams.csrf_userid }) {
                it[avatar] = changeAvatarParams.avatar
            }
        }
    }

    override suspend fun listAll(): List<UsersData> {
        return transaction {
            UserTable.selectAll().map {
                UsersData(
                    userId = it[UserTable.id],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    phone = it[UserTable.phone],
                    status = it[UserTable.status],
                    isBan = it[UserTable.isBan],
                    email = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    dateRegistered = it[UserTable.dateRegistered].toString(),
                )
            }
        }
    }

    override suspend fun listByName(filterUsersParams: com.example.dtos.FilterUsersParams): List<UsersData> {
        return transaction {
            val query = UserTable.selectAll()
            filterUsersParams.byName?.let {
                query.andWhere { UserTable.firstname eq it or (UserTable.lastname eq it) }
            }

            query.map {
                UsersData(
                    userId = it[UserTable.id],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    phone = it[UserTable.phone],
                    status = it[UserTable.status],
                    isBan = it[UserTable.isBan],
                    email = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    dateRegistered = it[UserTable.dateRegistered].toString(),
                )
            }
        }
    }


}