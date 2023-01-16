package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ArticleTable : Table("article") {
    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String?> = varchar("title", 255).nullable()
    val content: Column<String?> = varchar("content", 255).nullable()
    val lastDateEdited: Column<LocalDateTime> = datetime("last_date_edited").clientDefault {LocalDateTime.now() }
    val topicId: Column<Int> = integer("topic_id")
        .references(
            TopicTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_article_topic_id"
        )
    val dateCreated: Column<String?> = varchar("date_created", 20).nullable()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}