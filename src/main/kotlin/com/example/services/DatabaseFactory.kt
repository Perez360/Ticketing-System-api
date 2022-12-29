package com.example.services

import com.example.components.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private var hasRun = false
    private const val _username = "root"
    private const val _password = "root"
    private const val _driverClassName = "com.mysql.cj.jdbc.Driver"
    private const val _jdbcUrl = "jdbc:mysql://localhost:3306/mydb"
    private const val TRANSACTIONISOLATION = "TRANSACTION_REPEATABLE_READ"


    fun connect(applicationConfig: ApplicationConfig) {
        Database.connect(hikari(applicationConfig))
        runUpdateAndMigration()
    }

    private fun hikari(applicationConfig: ApplicationConfig): HikariDataSource {
        val hikari = HikariConfig().apply {
            this.jdbcUrl = _jdbcUrl
            this.username = _username
            this.password = _password
            this.driverClassName = _driverClassName
            this.isAutoCommit = false
            this.transactionIsolation = TRANSACTIONISOLATION
        }
        hikari.validate()
        return HikariDataSource(hikari)
    }
//      private fun hikari(): HikariDataSource {
//        val hikari = HikariConfig().apply {
//            this.jdbcUrl = _jdbcUrl
//            this.username = _username
//            this.password = _password
//            this.driverClassName = _driverClassName
//            this.isAutoCommit = false
//            this.transactionIsolation = TRANSACTIONISOLATION
//        }
//        hikari.validate()
//        return HikariDataSource(hikari)
//    }

    private fun runUpdateAndMigration() {
        if (!hasRun) {
            transaction {
                SchemaUtils.createMissingTablesAndColumns(UserTable)
            }
            hasRun = true
        }

    }
}