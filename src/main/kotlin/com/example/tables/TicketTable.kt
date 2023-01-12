package com.example.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TicketTable : Table("tickets") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstname: Column<String> = varchar("firstname", 255).index()
    val lastname: Column<String> = varchar("lastname", 255).index()
    val email: Column<String> = varchar("email", 255).index()
    val tagName:Column<String?> =varchar("tag_name",10).nullable()
    val tagColor:Column<String?> =varchar("tag_Color",10).nullable()
    val phone: Column<String> = varchar("phone", 10)
    val password: Column<String> = varchar("password", 255)
    val token: Column<String> = varchar("token", 255)
    val status: Column<Int> = integer("status")
    val dateRegistered: Column<LocalDateTime> = datetime("date_created", ).clientDefault { LocalDateTime.now() }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}