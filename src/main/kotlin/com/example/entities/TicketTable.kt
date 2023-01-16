package com.example.entities

import com.example.entities.TicketTable.nullable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.jws.soap.SOAPBinding.Use

object TicketTable : Table("ticket") {
    val id: Column<Int> = integer("id").autoIncrement()
    val ticketNumber: Column<Int> = integer("ticket_number").index(isUnique = true)
    val title: Column<String> = varchar("title", 255)
    val isRead: Column<Boolean> = bool("is_read")
    val isOpen: Column<Boolean> = bool("is_open")
    val isReadByStaff: Column<Boolean> = bool("is_read_by_staff")
    val content: Column<String> = varchar("content", 255)
    val language: Column<String> = varchar("language", 255)
    val isTitleEdited: Column<Boolean> = bool("is_title_edited")
    val isContentEdited: Column<Boolean> = bool("is_content_edited")
    val isOwner: Column<Boolean> = bool("is_owner")
    val ownerId: Column<Int?> = integer("owner_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_owner_id"
        ).nullable()
    val authorName: Column<String> = varchar("author_name", 255)
    val authorId: Column<Int?> = integer("author_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_author_id"
        ).nullable()
    val authorEmail: Column<String> = varchar("author_email", 255)
    val isAuthorStaff: Column<Boolean> = bool("is_author_staff")
    val authorStaffId: Column<Int?> = integer("author_staff_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_department_id"
        ).nullable()
    val isStaff: Column<Boolean> = bool("is_staff")
    val departmentId: Column<Int?> = integer("department_id")
        .references(
            DepartmentTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_ticket_author_department_id"
        ).nullable()
    val dateCreated: Column<LocalDateTime> = datetime("date_created").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))
    }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}