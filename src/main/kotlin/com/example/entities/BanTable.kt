package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object BanTable : Table("ban") {
    val id: Column<Int> = integer("id").autoIncrement()
    val email: Column<String?> = varchar("email", 255).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}