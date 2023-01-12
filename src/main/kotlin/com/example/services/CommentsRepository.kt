package com.example.services

import com.example.models.Comment

interface CommentsRepository {
    suspend fun create(comment: Comment):Int
    suspend fun delete(commentID:Int):Int
    suspend fun updateTitle(commentID: Int,newTitle:String):Int
    suspend fun updateContent(commentID: Int,newContent:String):Int
    suspend fun listAll():List<Comment>
}