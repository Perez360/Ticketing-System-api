package com.example.contollers.impl

import com.example.services.CommentsRepository
import com.example.models.Comment
import com.example.contollers.CommentController
import com.example.dtos.user.CommentsDto
import com.example.shared.APIResponse
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException

class CommentControllerImpl : CommentController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val kodein = Kodein {
        bind<CommentController>() with singleton { CommentControllerImpl() }
    }
    private val commentDao: CommentsRepository = kodein.instance()

    override suspend fun addComment(commentsDto: CommentsDto) {

    }

    override suspend fun getAllComments(): APIResponse<Comment> {
        val response=try {
            val listOfComments=commentDao.listAll()
            if (listOfComments.isNotEmpty()){
                APIResponse(HttpStatusCode.OK.value, "Comments users", listOfComments)
            }else{
                APIResponse(HttpStatusCode.OK.value, "No comments found", emptyList())
            }
        }catch (se:SQLException){
            log.warn("An error occurred when processing sale", se)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }

    override suspend fun deleteAComment(commentID: Int): APIResponse<String> {
        val response: APIResponse<String> = try {
            if (commentDao.delete(commentID) > 0) {
                APIResponse(
                    HttpStatusCode.Gone.value, "UserPart_secured successfully deleted", listOf()
                )
            } else {
                APIResponse(HttpStatusCode.NoContent.value, "UserPart_secured does not exist", listOf())
            }
        } catch (sql: SQLException) {
            log.warn("An error occurred when processing sale", sql)
            APIResponse(HttpStatusCode.InternalServerError.value, "Internal Server Error", listOf())
        }
        return response
    }
}