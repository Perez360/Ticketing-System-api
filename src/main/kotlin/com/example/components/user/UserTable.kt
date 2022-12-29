package com.example.components.user

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstname: Column<String> = varchar("firstname", 255).index()
    val lastname: Column<String> = varchar("lastname", 255).index()
    val email: Column<String> = varchar("email", 255)
    val phone: Column<String> = varchar("phone",10)
    val password: Column<String> = varchar("password", 255)

    override val primaryKey: PrimaryKey? = PrimaryKey(id)
}