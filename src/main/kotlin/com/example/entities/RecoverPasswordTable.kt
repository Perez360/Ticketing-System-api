package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object RecoverPasswordTable : Table("recoverpassword") {
    val id: Column<Int> = integer("id").autoIncrement()
    val isStaff: Column<Boolean> = bool("isStaff").clientDefault { false }
    val token: Column<String?> = varchar("token",255).nullable()
    val dateCreated: Column<LocalDateTime> = datetime("date_created").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))
    }
    override val primaryKey = PrimaryKey(id)
}