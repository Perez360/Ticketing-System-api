package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object SessionCookieTable : Table("sessioncookie") {
    val id: Column<Int> = integer("id").autoIncrement()
    val ip: Column<String?> = varchar("ip", 255).nullable()
    val token: Column<String?> = varchar("token", 255).nullable()
    val user_id: Column<Int> = integer("user_id")
        .references(
            UserTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_sessioncookie_user_id"
        )
    val dateCreated: Column<LocalDateTime> = datetime("date_created").clientDefault {LocalDateTime.now()}
    override val primaryKey = PrimaryKey(id)

}