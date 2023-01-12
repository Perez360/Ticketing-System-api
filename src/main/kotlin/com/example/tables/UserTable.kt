package com.example.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : Table("users") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstname: Column<String> = varchar("firstname", 255).index()
    val lastname: Column<String> = varchar("lastname", 255).index()
    val email: Column<String> = varchar("email", 255)
    val phone: Column<String> = varchar("phone", 10)
    val staff: Column<Boolean> = bool("staff")
    val avatar:Column<String?> =varchar("avatar",255).nullable()
    val isBan:Column<Int> = integer("is_ban")
    val status:Column<Int> = integer("status")
    val password: Column<String> = varchar("password", 255)
    val token: Column<String?> = varchar("token", 255).nullable()
    val dateRegistered= datetime("date_registered").clientDefault {LocalDateTime.now()}
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}