package home.projectmanagementsystem.repositories

import home.projectmanagementsystem.models.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository : MongoRepository<Comment, String>{
    fun findCommentByIdAndTaskId(commentId: String, taskId: String): Comment?
    fun findCommentsByTaskId(taskId: String): MutableList<Comment>
    fun findCommentsByCreatedBy(userId: String): MutableList<Comment>
}