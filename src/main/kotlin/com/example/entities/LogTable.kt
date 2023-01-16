package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LogTable : Table("log") {
    val id: Column<Int> = integer("id").autoIncrement()
    val type: Column<String?> = varchar("type", 255).nullable()
    val date: Column<LocalDateTime> = datetime("date").clientDefault {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter))
    }
    val authorName: Column<String?> = varchar("author_name", 255).nullable()
    val authorUserId: Column<Int?> = integer("author_staff_id")
        .references(
            UserTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_log_author_user_id"
        ).nullable()
    val authorStaffId: Column<Int?> = integer("author_user_id")
        .references(
            StaffTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_log_author_staff_id"
        ).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}