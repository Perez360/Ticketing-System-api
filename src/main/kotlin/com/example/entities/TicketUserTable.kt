package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TicketUserTable : Table("ticket_user") {
    val id: Column<Int> = integer("id").autoIncrement()
    val ticketId: Column<Int?> = integer("ticket_id")
        .references(
            TicketTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_user_ticket_id"
        ).nullable()
    val userId: Column<Int?> = integer("user_id")
        .references(
            UserTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_user_user_id"
        ).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}