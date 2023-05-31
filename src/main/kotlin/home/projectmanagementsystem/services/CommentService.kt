package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Comment
import home.projectmanagementsystem.repositories.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(private val commentRepository: CommentRepository) {
    fun findCommentByIdAndTaskId(commentId: String, taskId: String): Comment? = commentRepository.findCommentByIdAndTaskId(commentId, taskId)
    fun findCommentsByTaskId(taskId: String): MutableList<Comment> = commentRepository.findCommentsByTaskId(taskId)
    fun save(comment: Comment): Comment = commentRepository.save(comment)
}