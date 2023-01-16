package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UserTicketsTable : Table("user_ticket") {
    val id: Column<Int> = integer("id").autoIncrement()
    val userId: Column<Int> = integer("user_id")
        .references(
            UserTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_user_user_id"
        )
    val ticketId: Column<Int> = integer("ticket_id")
        .references(
            UserTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_user_ticket_id"
        )


    override val primaryKey: PrimaryKey = PrimaryKey(id)
}