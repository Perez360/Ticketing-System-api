package com.example.services.impl

import com.example.dtos.user.*
import com.example.entities.UserTable
import com.example.models.FullUserData
import com.example.models.SignupUserData
import com.example.models.UsersData
import com.example.security.TokenGenerator
import com.example.services.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(signupUserDto: SignupUserDto): SignupUserData? {
        fun rowToUserReg(resultRow: ResultRow?): SignupUserData? {

            return if (resultRow == null) null
            else {
                SignupUserData(
                    userId = resultRow[UserTable.id],
                    email = resultRow[UserTable.email],
                    isVerified = resultRow[UserTable.isVerified],
                    dateRegistered = resultRow[UserTable.signupDate].toString()
                )
            }
        }

        val insertStatement: InsertStatement<Number> = transaction {
            UserTable.insert {
                it[firstname] = signupUserDto.firstname!!
                it[lastname] = signupUserDto.lastname!!
                it[lastname] = signupUserDto.lastname!!
                it[email] = signupUserDto.email!!
                it[phone] = signupUserDto.phone!!
                it[password] = signupUserDto.password!!
            }
        }
        return rowToUserReg(insertStatement.resultedValues?.get(0))
    }

    override suspend fun update(loginDto: LoginDto): Int {
        return transaction {
            UserTable.update({ UserTable.email eq loginDto.email }) {
                it[isOnline] = true
            }
        }
    }

    override suspend fun update(verifyEmailDto: VerifyEmailDto): Int {
        return transaction {
            UserTable.update({ UserTable.email eq verifyEmailDto.userEmail }) {
                it[isVerified] = true
            }
        }
    }


    override suspend fun getById(userID: Int): FullUserData? {
        return transaction {
            UserTable.select { UserTable.id eq userID }.map {
                FullUserData(
                    csrf_userid = it[UserTable.id],
                    userEmail = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    phone = it[UserTable.phone],
                    isBan = it[UserTable.isBan],
                    isStaff = it[UserTable.isStaff],
                    isOnline = it[UserTable.isOnline],
                    tickets = it[UserTable.tickets],
                    isverifield = it[UserTable.isVerified],
                    verificationToken = it[UserTable.verificationToken],
                    password = it[UserTable.password],
                    signupDate = it[UserTable.signupDate].toString()
                )
            }.singleOrNull()
        }

    }

    // This method can only be used by owner on the account
    override suspend fun getByEmail(userEmail: String): FullUserData? {
        return transaction {
            val query = UserTable.select(UserTable.email eq userEmail)
            query.map {
                FullUserData(
                    csrf_userid = it[UserTable.id],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    userEmail = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    phone = it[UserTable.phone],
                    password = it[UserTable.password],
                    isBan = it[UserTable.isBan],
                    isverifield = it[UserTable.isVerified],
                    isOnline = it[UserTable.isOnline],
                    signupDate = it[UserTable.signupDate].toString(),
                    isStaff = it[UserTable.isStaff],
                    verificationToken = it[UserTable.verificationToken],
                    tickets = it[UserTable.tickets]
                )
            }.singleOrNull()

        }
    }

    override suspend fun delete(deleteUserDto: DeleteUserDto): Int {
        return transaction {
            UserTable.deleteWhere { id eq deleteUserDto.userID }
        }
    }

    override suspend fun update(editPasswordParams: EditPasswordDto): Int {
        return transaction {
            UserTable.update({ UserTable.id eq editPasswordParams.csrf_userid }) {
                it[password] = editPasswordParams.newPassword
            }
        }

    }

    override suspend fun update(editPhoneDto: EditPhoneDto): Int {

        return transaction {
            UserTable.update({ UserTable.id eq editPhoneDto.csrf_userid }) {
                it[password] = editPhoneDto.newPhoneNumber
            }
        }
    }

    override  fun deleteVerificationToken(userEmail: String): Int {
         return transaction {
             UserTable.update({ UserTable.email eq userEmail }) {
                 it[verificationToken] = null
             }
        }
    }

    override suspend fun update(banEmailDto: BanEmailDto): Int {
        return transaction {
            UserTable.update({ UserTable.id eq banEmailDto.csrf_userid }) {
                it[isBan] = true
            }
        }
    }

    override suspend fun update(sendPasswordRecoveryDto: SendPasswordRecoveryDto): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(editEmailDto: EditEmailDto): Int {
        return transaction {
            UserTable.update({ UserTable.id eq editEmailDto.csrf_userid }) {
                it[email] = editEmailDto.newEmail
            }
        }
    }

    override suspend fun update(editAvatarDto: ChangeAvatarDto): Int {
        return transaction {
            UserTable.update({ UserTable.id eq editAvatarDto.csrf_userid }) {
                it[avatar] = editAvatarDto.avatar
            }
        }
    }

    override suspend fun update(userEmail: String): Int {
        return transaction {
            UserTable.update({ UserTable.email eq userEmail }) {
                it[verificationToken] = TokenGenerator.getToken()
            }
        }
    }

    override suspend fun update(logoutDto: LogoutDto): Int {
        return transaction {
            UserTable.update({ UserTable.id eq logoutDto.csrf_userid }) {
                it[isOnline] = false
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
                    isOnline = it[UserTable.isOnline],
                    email = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    signupDate = it[UserTable.signupDate].toString(),
                )
            }
        }
    }

    override suspend fun filterByName(filterUsersDto: FilterUsersDto): List<UsersData> {
        return transaction {
            val query = UserTable.selectAll()
            filterUsersDto.byName?.let {
                query.andWhere { UserTable.firstname eq it or (UserTable.lastname eq it) }
            }

            query.map {
                UsersData(
                    userId = it[UserTable.id],
                    firstName = it[UserTable.firstname],
                    lastName = it[UserTable.lastname],
                    phone = it[UserTable.phone],
                    isOnline = it[UserTable.isOnline],
                    email = it[UserTable.email],
                    avatar = it[UserTable.avatar],
                    signupDate = it[UserTable.signupDate].toString(),
                )
            }
        }
    }


}