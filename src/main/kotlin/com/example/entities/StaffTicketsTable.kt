package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StaffTicketsTable : Table("staff_ticket") {
    val id: Column<Int> = integer("id").autoIncrement()
    val dateCreated = datetime("date_created").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))
    }
    val ticketId = integer("ticket_id")
        .references(
            TicketTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_staff_ticket_ticket_id"
        ).nullable()
    val staffId = integer("staff_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_staff_ticket_staff_id"
        ).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}