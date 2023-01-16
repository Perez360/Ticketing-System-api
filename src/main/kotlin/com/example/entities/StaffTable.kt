package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StaffTable : Table("staff") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstname: Column<String> = varchar("firstname", 255).index()
    val lastname: Column<String> = varchar("lastname", 255).index()
    val email: Column<String> = varchar("email", 255)
    val phone: Column<String> = varchar("phone", 10)
    val level: Column<Int?> = integer("level").nullable()
    val avatar: Column<String?> = varchar("avatar", 255).nullable()
    val verificationToken: Column<String?> = varchar("verification_token", 255).nullable()
    val isBan: Column<Boolean> = bool("is_ban").clientDefault { false }
    val isOnline: Column<Boolean> = bool("is_online").clientDefault { false }
    val isVerified: Column<Boolean> = bool("is_verified").clientDefault { false }
    val password: Column<String> = varchar("password", 255)
    val dateRegistered: Column<LocalDateTime> = datetime("signup_date").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))
    }
    val lastLoginDate: Column<LocalDateTime?> = datetime("last_login_date").nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}