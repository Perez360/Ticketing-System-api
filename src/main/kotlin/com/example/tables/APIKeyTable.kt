package com.example.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object APIKeyTable : Table("apikeys") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255).index()
    val token: Column<String> = varchar("token", 255)
    val authorName: Column<String> = varchar("author_name", 255).index()
    val description: Column<String> = varchar("description", 255)
    val dateCreated: Column<String> = varchar("date_created", 20)
    val canCreateUsers: Column<Int> = integer("can_create_users")
    val canCreateTickets: Column<Int> = integer("can_delete_tickets")
    val canCheckTickets: Column<Int> = integer("can_check_tickets")
    val shouldReturnTicketNumber: Column<Int> = integer("can_return_ticket_number")
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}