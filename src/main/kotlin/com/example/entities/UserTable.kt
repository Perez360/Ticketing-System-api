package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : Table("user") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstname: Column<String> = varchar("firstname", 255).index()
    val lastname: Column<String> = varchar("lastname", 255).index()
    val email: Column<String> = varchar("email", 255)
    val phone: Column<String> = varchar("phone", 10)
    val password: Column<String> = varchar("password", 255)
    val avatar: Column<String?> = varchar("avatar", 255).nullable()
    val isStaff: Column<Boolean> = bool("is_staff").clientDefault { false }
    val isBan: Column<Boolean> = bool("is_ban").clientDefault { false }
    val isOnline: Column<Boolean> = bool("is_online").clientDefault { false }
    val isVerified: Column<Boolean> = bool("is_verified").clientDefault { false }
    val tickets: Column<Int> = integer("tickets").clientDefault { 0 }
    val signupDate = datetime("signup_date").clientDefault { LocalDateTime.now() }
    val verificationToken: Column<String?> = varchar("verification_token", 255).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}