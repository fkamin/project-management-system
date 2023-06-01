package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Comment
import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.services.CommentService
import home.projectmanagementsystem.services.ProjectService
import home.projectmanagementsystem.services.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
class CommentController(
    private val commentService: CommentService,
    private val taskService: TaskService,
    private val projectService: ProjectService) {
    @GetMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    fun getCommentFromTask(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String,
        @PathVariable commentId: String): CommentDto {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)
        validateTaskApiExceptions(taskId, projectId)
        val comment = validateCommentApiExceptionsAndIfValidatedReturnComment(commentId, taskId)

        return comment.toDto()
    }

    @GetMapping("/{projectId}/tasks/{taskId}/comments")
    fun getCommentsFromTask(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String): List<CommentDto> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)
        validateTaskApiExceptions(taskId, projectId)

        return commentService.findCommentsByTaskId(taskId).map { comment -> comment.toDto() }
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    fun createAndAddCommentToTask(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String,
        @RequestBody payload: CreateCommentDto
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)
        validateTaskApiExceptions(taskId, projectId)

        val comment = Comment(
            taskId = payload.taskId,
            createdBy = authUser.id,
            createdAt = payload.createdAt,
            content = payload.content,
            wasEdited = false
        )

        commentService.save(comment)
        return ResponseEntity.ok("Pomyślnie dodano komentarz")
    }

    @PutMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    fun editComment(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String,
        @PathVariable commentId: String,
        @RequestBody payload: UpdateCommentDto
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)
        validateTaskApiExceptions(taskId, projectId)
        val comment = validateCommentApiExceptionsAndIfValidatedReturnComment(commentId, taskId)

        comment.content = payload.content
        comment.wasEdited = true

        commentService.save(comment)
        return ResponseEntity.ok("Pomyślnie dodano komentarz")
    }
    
    private fun validateProjectApiExceptions(projectId: String, authUser: User) {
        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.createdBy != authUser.id) throw ApiException(403, "To nie jest twój projekt")
    }
    private fun validateTaskApiExceptions(taskId: String, projectId: String) {
        val task =  taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Zadanie nie istnieje")
        if (task.id != taskId) throw ApiException(403, "To nie jest twój task")
    }
    private fun validateCommentApiExceptionsAndIfValidatedReturnComment(commentId: String, taskId: String): Comment{
        val comment = commentService.findCommentByIdAndTaskId(commentId, taskId) ?: throw ApiException(404, "Komentarz nie istnieje")
        if (comment.id != commentId) throw ApiException(403, "To nie jest twój komentarz")
        return comment
    }
}