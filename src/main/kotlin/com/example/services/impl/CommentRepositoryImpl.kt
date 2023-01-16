package com.example.services.impl

import com.example.entities.CommentsTable
import com.example.models.Comment
import com.example.services.CommentsRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class CommentRepositoryImpl : CommentsRepository {
    override suspend fun create(comment: Comment): Int {
        return transaction {
            CommentsTable.insert {
                it[authorID] = comment.authorID
                it[authorName] = comment.authorName
                it[content] = comment.content
                it[dateCommented] = comment.dateCommented
            }.insertedCount
        }
    }

    override suspend fun delete(commentID: Int): Int {
        return transaction {
            CommentsTable.deleteWhere { id eq commentID }
        }
    }

    override suspend fun updateTitle(commentID: Int,newTitle:String): Int {
        TODO("Not yet implemented")
    }
    override suspend fun updateContent(commentID: Int,newContent:String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(): List<Comment> {
        TODO("Not yet implemented")
    }
}