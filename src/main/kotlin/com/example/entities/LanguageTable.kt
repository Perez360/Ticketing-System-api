package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object LanguageTable : Table("language") {
    val id: Column<Int> = integer("id").autoIncrement()
    val code: Column<String?> = varchar("code",255).nullable()
    val allowed: Column<Boolean> = bool("allowed").clientDefault { true }
    val isSupported: Column<Boolean> = bool("is_supported").clientDefault { true }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}