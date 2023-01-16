package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CommentsTable : Table("comment"){
    val id:Column<Int> =integer("id").autoIncrement()
    val authorID:Column<Int> =integer("author_id")
    val authorName:Column<String> =varchar("author_name",255).index()
    val dateCommented:Column<String> =varchar("date_created" ,30)
    val content:Column<String> =varchar("content",255)
    override val primaryKey: PrimaryKey= PrimaryKey(id)
}