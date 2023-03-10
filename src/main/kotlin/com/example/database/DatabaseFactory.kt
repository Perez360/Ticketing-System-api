package com.example.database

import com.example.entities.*
import com.perezsite.services.databaseconfigurations.MysqlConfiguration
import com.perezsite.services.databaseconfigurations.PostgreSQLConfiguration
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private var hasRun = false
    private const val TRANSACTIONISOLATION = "TRANSACTION_REPEATABLE_READ"


    fun connect() {
        MysqlConfiguration.loadPopsFromHoconFile()
        Database.connect(
            hikariconnectMysqlWithConf()
        )
        runUpdateAndMigration()

    }


    private fun hikariconnectMysqlWithConf(): HikariDataSource {
        val hikari = HikariConfig().apply {
            jdbcUrl = MysqlConfiguration.getSystemProperties().getProperty("mysql_jdbcUrl")
            username = MysqlConfiguration.getSystemProperties().getProperty("mysql_username")
            password = MysqlConfiguration.getSystemProperties().getProperty("mysql_password")
            driverClassName =
                MysqlConfiguration.getSystemProperties().getProperty("mysql_driverClassName")
            isAutoCommit = false
            maximumPoolSize = 10
            transactionIsolation = TRANSACTIONISOLATION
        }
        hikari.validate()
        return HikariDataSource(hikari)
    }

    private fun hikariconnectMysqlWithProps(): HikariDataSource {

        val hikari = HikariConfig().apply {
            jdbcUrl = MysqlConfiguration.getSystemProperties().getProperty("hikari.mysql.dataSource.jdbcUrl")
            username = MysqlConfiguration.getSystemProperties().getProperty("hikari.mysql.dataSource.user")
            password =
                MysqlConfiguration.getSystemProperties().getProperty("hikari.mysql.dataSource.password")
            driverClassName =
                MysqlConfiguration.getSystemProperties().getProperty("hikari.mysql.dataSource.driverClassName")
            isAutoCommit = false
            maximumPoolSize = 10
            transactionIsolation = TRANSACTIONISOLATION
        }
        hikari.validate()
        return HikariDataSource(hikari)
    }

    private fun hikariconnectPostgreWithConf(): HikariDataSource {
        val hikari = HikariConfig().apply {
            jdbcUrl = PostgreSQLConfiguration.getSystemProperties().getProperty("psql_jdbcUrl")
            username = PostgreSQLConfiguration.getSystemProperties().getProperty("psql_username")
            password = PostgreSQLConfiguration.getSystemProperties().getProperty("psql_password")
            driverClassName = PostgreSQLConfiguration.getSystemProperties().getProperty("psql_driverClassName")
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = TRANSACTIONISOLATION
        }
        hikari.validate()
        return HikariDataSource(hikari)
    }

    private fun hikariconnectPostgreWithProps(): HikariDataSource {
        val hikari = HikariConfig().apply {
            jdbcUrl =
                PostgreSQLConfiguration.getSystemProperties().getProperty("hikari.postgresql.dataSource.jdbcUrl")
            username =
                PostgreSQLConfiguration.getSystemProperties().getProperty("hikari.postgresql.dataSource.username")
            password =
                PostgreSQLConfiguration.getSystemProperties().getProperty("hikari.postgresql.dataSource.password")
            driverClassName =
                PostgreSQLConfiguration.getSystemProperties()
                    .getProperty("hikari.postgresql.dataSource.driverClassName")
            isAutoCommit = false
            maximumPoolSize = 10
            transactionIsolation = TRANSACTIONISOLATION
        }
        hikari.validate()
        return HikariDataSource(hikari)
    }


    private fun runUpdateAndMigration() {
        if (!hasRun) {
            transaction {
                addLogger(StdOutSqlLogger)

                SchemaUtils.createMissingTablesAndColumns(APIKeyTable)
                SchemaUtils.createMissingTablesAndColumns(ArticleTable)
                SchemaUtils.createMissingTablesAndColumns(BanTable)
                SchemaUtils.createMissingTablesAndColumns(CommentsTable)
                SchemaUtils.createMissingTablesAndColumns(DepartmentStaffTable)
                SchemaUtils.createMissingTablesAndColumns(DepartmentTable)
                SchemaUtils.createMissingTablesAndColumns(LanguageTable)
                SchemaUtils.createMissingTablesAndColumns(LogTable)
                SchemaUtils.createMissingTablesAndColumns(RecoverPasswordTable)
                SchemaUtils.createMissingTablesAndColumns(SessionCookieTable)
                SchemaUtils.createMissingTablesAndColumns(StaffTable)
                SchemaUtils.createMissingTablesAndColumns(StaffTicketsTable)
                SchemaUtils.createMissingTablesAndColumns(TagTable)
                SchemaUtils.createMissingTablesAndColumns(TagTicketTable)
                SchemaUtils.createMissingTablesAndColumns(TicketTable)
                SchemaUtils.createMissingTablesAndColumns(TopicTable)
                SchemaUtils.createMissingTablesAndColumns(UserTable)
                SchemaUtils.createMissingTablesAndColumns(UserTicketsTable)






            }
            hasRun = true
        }

    }
}