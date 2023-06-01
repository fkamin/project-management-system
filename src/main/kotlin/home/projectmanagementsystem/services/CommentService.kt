package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Comment
import home.projectmanagementsystem.repositories.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(private val commentRepository: CommentRepository) {
    fun findCommentByIdAndTaskId(commentId: String, taskId: String): Comment? = commentRepository.findCommentByIdAndTaskId(commentId, taskId)
    fun findCommentsByTaskId(taskId: String): MutableList<Comment> = commentRepository.findCommentsByTaskId(taskId)
    fun findCommentsByUserId(userId: String): MutableList<Comment> = commentRepository.findCommentsByCreatedBy(userId)
    fun save(comment: Comment): Comment = commentRepository.save(comment)
    fun delete(comment: Comment): Unit = commentRepository.delete(comment)
    fun deleteCommentsByUserId(userId: String) {
        val comments = findCommentsByUserId(userId)
        for (comment in comments) delete(comment)
    }
}