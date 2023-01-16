package com.example.entities

import com.example.entities.StaffTicketsTable.nullable
import com.example.entities.StaffTicketsTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TagTicketTable : Table("tag_ticket") {
    val id: Column<Int> = integer("id").autoIncrement()
    val tag_id: Column<Int?> = integer("tag_id")
        .references(
            TagTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_tag_ticket_tag_id"
        ).nullable()
    val ticket_id: Column<Int?> = integer("ticket_id")
        .references(
            TicketTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_tag_ticket_ticket_id"
        ).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}