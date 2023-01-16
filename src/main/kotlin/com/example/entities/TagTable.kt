package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TagTable : Table("tag") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String?> = varchar("name", 255).nullable()
    val color: Column<String?> = varchar("color", 255).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}