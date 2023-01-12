package com.example.contollers

import com.example.models.Comment
import com.example.shared.APIResponse

interface CommentController {
    suspend fun addComment(commentsParams: com.example.dtos.CommentsParams)
    suspend fun getAllComments():APIResponse<Comment>
    suspend fun deleteAComment(commentID:Int):APIResponse<String>
}