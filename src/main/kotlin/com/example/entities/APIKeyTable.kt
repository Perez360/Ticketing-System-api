package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object APIKeyTable : Table("apikey") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String?> = varchar("name", 255).nullable()
    val token: Column<String?> = varchar("token", 255).nullable()
    val authorStaffId: Column<Int> = integer("author_staff_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "fk_apikey_author_staff_id"
        )
    val authorStaffName: Column<String?> = varchar("author_staff_name", 255).nullable()
    val description: Column<String?> = varchar("description", 255).nullable()
    val dateCreated: Column<LocalDateTime> = datetime("date_created").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))

    }
    val canCreateUsers: Column<Boolean> = bool("can_create_users").clientDefault { false }
    val canCreateTickets: Column<Boolean> = bool("can_delete_tickets").clientDefault { false }
    val canCheckTickets: Column<Boolean> = bool("can_check_tickets").clientDefault { false }
    val shouldReturnTicketNumber: Column<Boolean> = bool("can_return_ticket_number").clientDefault { false }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}