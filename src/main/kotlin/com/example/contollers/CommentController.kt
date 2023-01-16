package com.example.contollers

import com.example.dtos.user.CommentsDto
import com.example.models.Comment
import com.example.shared.APIResponse

interface CommentController {
    suspend fun addComment(commentsDto: CommentsDto)
    suspend fun getAllComments():APIResponse<Comment>
    suspend fun deleteAComment(commentID:Int):APIResponse<String>
}