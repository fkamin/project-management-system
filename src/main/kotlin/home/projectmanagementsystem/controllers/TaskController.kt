package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Task
import home.projectmanagementsystem.services.ProjectService
import home.projectmanagementsystem.services.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
class TaskController(
    private val taskService: TaskService,
    private val projectService: ProjectService) {

    @GetMapping("/admin/tasks")
    fun getAllTasksByAdmin(): List<TaskDto> = taskService.findAllTasks().map { task -> task.toDto() }


    @GetMapping("/{projectId}/tasks/{taskId}")
    fun getTaskFromProject(authentication: Authentication, @PathVariable projectId: String, @PathVariable taskId: String): TaskDto {
        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie istnieje")
        return task.toDto()
    }

    @GetMapping("/{projectId}/tasks")
    fun getTasksFromProject(authentication: Authentication, @PathVariable projectId: String): List<TaskDto> {
        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        return taskService.findTasksByProjectId(projectId).map { task -> task.toDto() }
    }

    @PostMapping("/{projectId}/tasks")
    fun createAndAddTaskToProject(
        authentication: Authentication,
        @RequestBody payload: CreateTaskDto,
        @PathVariable projectId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (project.userId != authUser.id) throw ApiException(403, "To nie twój projekt")

        val task = Task(
            title = payload.title,
            description = payload.description,
            deadline = payload.deadline,
            state = payload.state,
            categories = payload.categories,
            projectId = projectId
        )

        taskService.addTaskToProject(projectId, task)
        return ResponseEntity.ok("Pomyślnie dodano taska: ${task.title}, do projektu: ${project.title}")
    }

    @PutMapping("/{projectId}/tasks/{taskId}")
    fun updateTaskInProject(
        authentication: Authentication,
        @RequestBody payload: UpdateTaskDto,
        @PathVariable projectId: String,
        @PathVariable taskId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val task = taskService.findTaskByIdAndProjectId(taskId, projectId) ?: throw ApiException(404, "Task nie znaleziony")
        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (authUser.id != project.userId) throw ApiException(403, "To nie jest twój task")

        task.title = payload.title
        task.description = payload.description
        task.deadline = payload.deadline
        task.state = payload.state
        task.categories = payload.categories

        taskService.save(task)
        projectService.updateTaskInProject(task.projectId, taskId, task.id.toString())

        return ResponseEntity.ok("Pomyślnie zaktualizowano taska")
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    fun deleteTaskFromProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @PathVariable taskId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val task = taskService.findTaskById(taskId) ?: throw ApiException(404, "Task nie znaleziony")
        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (authUser.id != project.userId) throw ApiException(403, "To nie jest twój task")

        taskService.delete(task)
        projectService.deleteTaskFromProject(projectId, taskId)

        return ResponseEntity.ok("Pomyślnie usunięto taska")
    }

    @DeleteMapping("/{projectId}/tasks")
    fun deleteTasksFromProject(authentication: Authentication, @PathVariable projectId: String): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (authUser.id != project.userId) throw ApiException(403, "To nie jest twój task")

        taskService.deleteTasksFromProject(projectId)
        projectService.deleteTasksFromProject(projectId)
        return ResponseEntity.ok("Pomyślnie usunięto wszystkie taski")
    }
}