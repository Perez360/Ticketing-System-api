package com.example.components.user.dao

import User
import com.example.components.user.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserDaoImpl : UserDao {
    override suspend fun create(user: User): Int {
        return transaction {
            UserTable.insert {
                it[firstname] = user.firstname
                it[lastname] = user.lastname
                it[email] = user.email
                it[phone] = user.phone
                it[password] = user.password
            }
        }.insertedCount
    }

    override suspend fun get(userID: Int): User? {
        return transaction {
            UserTable.select { UserTable.id eq userID }
                .map {
                    User(
                        id = it[UserTable.id],
                        firstname = it[UserTable.firstname],
                        lastname = it[UserTable.lastname],
                        email = it[UserTable.email],
                        phone = it[UserTable.phone],
                        password = it[UserTable.password]
                    )
                }.singleOrNull()
        }
    }

    override suspend fun delete(userID: Int): Int {
        return transaction {
            UserTable.deleteWhere { id eq userID }
        }
    }

    override suspend fun update(user: User): Int {
        return transaction {
            UserTable.update(where = { UserTable.id eq user.id }) {
                it[this.password] = user.password
                it[this.phone] = user.phone



            }
        }
    }


    override suspend fun list(start: Int, size: Int): List<User> {
        return transaction {
            val query = UserTable.selectAll()
            query.limit(size, ((start - 1) * size).toLong())
            query.map {
                User(
                    id = it[UserTable.id],
                    firstname = it[UserTable.firstname],
                    lastname = it[UserTable.lastname],
                    email = it[UserTable.email],
                    phone = it[UserTable.phone],
                    password = it[UserTable.password]
                )
            }
        }
    }

    override suspend fun listByName(startIndex: Int, size: Int, byName: String?): List<User> {
        return transaction {
            val query = UserTable.selectAll()
            byName?.let {
                query.andWhere { UserTable.firstname eq it }
            }
            byName?.let {
                query.andWhere { UserTable.lastname eq it }
            }

            query.limit(size, ((startIndex - 1) * size).toLong())
            query.map {
                User(
                    id = it[UserTable.id],
                    firstname = it[UserTable.firstname],
                    lastname = it[UserTable.lastname],
                    email = it[UserTable.email],
                    phone = it[UserTable.phone],
                    password = it[UserTable.password],
                )
            }
        }
    }

    override fun size(): Int=9

}