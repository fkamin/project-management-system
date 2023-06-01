package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Task
import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.services.ProjectService
import home.projectmanagementsystem.services.TaskService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
@SecurityRequirement(name = "Bearer Authentication")
class TaskController(
    private val taskService: TaskService,
    private val projectService: ProjectService) {

    @GetMapping("/{projectId}/tasks/{taskId}")
    fun getTaskFromProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String): TaskDto {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Zadanie nie istnieje")
        return task.toDto()
    }

    @GetMapping("/{projectId}/tasks")
    fun getTasksFromProject(
        authentication: Authentication,
        @PathVariable projectId: String): List<TaskDto> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        return taskService.findTasksByProjectId(projectId).map { task -> task.toDto() }
    }

    @PostMapping("/{projectId}/tasks")
    fun createAndAddTaskToProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @RequestBody payload: CreateTaskDto): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        val task = Task(
            projectId = projectId,
            createdBy = authUser.id,
            listOfCategories = payload.listOfCategories,
            title = payload.title,
            description = payload.description,
            state = payload.state,
        )

        taskService.save(task)
        return ResponseEntity.ok("Pomyślnie dodano zadanie '${payload.title}' do projektu")
    }

    @PutMapping("/{projectId}/tasks/{taskId}")
    fun updateTaskInProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String,
        @RequestBody payload: UpdateTaskDto): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        val task = validateTaskApiExceptionsAndIfValidatedReturnTask(taskId, projectId)
        task.listOfCategories = payload.listOfCategories
        task.title = payload.title
        task.description = payload.description
        task.state = payload.state

        taskService.save(task)
        return ResponseEntity.ok("Pomyślnie zaktualizowano zadanie '${payload.title}'")
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    fun deleteTaskFromProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        val task = validateTaskApiExceptionsAndIfValidatedReturnTask(taskId, projectId)
        val taskName = task.title

        taskService.delete(task)
        return ResponseEntity.ok("Pomyślnie usunięto zadanie '$taskName'")
    }

    @DeleteMapping("/{projectId}/tasks")
    fun deleteTasksFromProject(
        authentication: Authentication,
        @PathVariable projectId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        validateProjectApiExceptions(projectId, authUser)

        taskService.deleteTasksFromProject(projectId)
        return ResponseEntity.ok("Pomyślnie usunięto wszystkie zadania")
    }

    private fun validateProjectApiExceptions(
        projectId: String,
        authUser: User
    ) {
        val project = projectService.findProjectByIdAndUserId(projectId, authUser.id) ?: throw ApiException(404, "Szukany projekt nie istnieje")
        if (authUser.id != project.createdBy) throw ApiException(403, "To nie jest twój projekt")
    }

    private fun validateTaskApiExceptionsAndIfValidatedReturnTask(
        taskId: String,
        projectId: String,
    ): Task {
        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        if (task.projectId != projectId) throw ApiException(403, "To nie twój task")
        return task
    }
}