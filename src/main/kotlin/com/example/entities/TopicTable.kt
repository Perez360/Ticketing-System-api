package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TopicTable : Table("topic") {
    val id: Column<Int> = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
}
