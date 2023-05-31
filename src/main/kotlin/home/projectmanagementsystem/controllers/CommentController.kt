package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Comment
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

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        if (!project.taskList.contains(task.id)) throw ApiException(403, "To nie twój task")

        val comment = commentService.findCommentByIdAndTaskId(commentId, taskId) ?: throw ApiException(404, "Komentarz nie istnieje")
        if (!task.comments.contains(comment.id)) throw ApiException(403, "To nie twój komentarz")

        return comment.toDto()
    }

    @GetMapping("/{projectId}/tasks/{taskId}/comments")
    fun getCommentsFromTask(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String): List<CommentDto> {

        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        if (!project.taskList.contains(task.id)) throw ApiException(403, "To nie twój task")

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

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        if (!project.taskList.contains(task.id)) throw ApiException(403, "To nie twój task")

        val comment = Comment(
            description = payload.description,
            author = authUser.id,
            taskId = taskId,
            date = payload.date
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

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        if (!project.taskList.contains(task.id)) throw ApiException(403, "To nie twój task")

        val comment = commentService.findCommentByIdAndTaskId(commentId, taskId) ?: throw ApiException(404, "Komentarz nie istnieje")
        if (!task.comments.contains(comment.id)) throw ApiException(403, "To nie twój komentarz")

        comment.description = payload.description
        comment.date = payload.date
        comment.wasEdited = true

        commentService.save(comment)
        taskService.updateCommentInTask(comment.taskId, commentId, comment.id.toString())
        return ResponseEntity.ok("Pomyślnie dodano komentarz")
    }

}