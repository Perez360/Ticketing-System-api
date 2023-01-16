package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object DepartmentTable : Table("department") {
    val id: Column<Int> = integer("id").autoIncrement()
    val owners: Column<Int> = integer("owners")
    val name: Column<String?> = varchar("name", 255).nullable()
    val isPrivate: Column<Boolean> = bool("is_private").clientDefault { false }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}