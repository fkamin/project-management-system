package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Project
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
class ProjectController(
    private val projectService: ProjectService,
    private val taskService: TaskService) {

    @PostMapping
    fun addProject(
        authentication: Authentication,
        @RequestBody payload: CreateProjectDto): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = Project(
            createdBy = authUser.id,
            title = payload.title,
            description = payload.description
        )

        projectService.save(project)
        return ResponseEntity.ok("Pomyślnie utworzono projekt '${payload.title}'")
    }

    @GetMapping("/{projectId}")
    fun getProject(
        authentication: Authentication,
        @PathVariable projectId: String): ProjectDto {
        val authUser = authentication.toUser()

        val project = validateProjectApiExceptionsAndIfValidatedReturnProject(projectId, authUser)

        return project.toDto()
    }

    @GetMapping
    fun getProjects(authentication: Authentication): List<ProjectDto> {
        val authUser = authentication.toUser()

        return projectService.findProjectsByUserId(authUser.id).map { project -> project.toDto() }
    }

    @PutMapping("/{projectId}")
    fun updateProject(
        authentication: Authentication,
        @PathVariable projectId: String,
        @RequestBody payload: UpdateProjectDto): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = validateProjectApiExceptionsAndIfValidatedReturnProject(projectId, authUser)

        project.title = payload.title
        project.description = payload.description

        projectService.save(project)
        return ResponseEntity.ok("Pomyślnie zaktualizowano projekt '${payload.title}'")
    }

    @DeleteMapping("/{projectId}")
    fun deleteProject(
        authentication: Authentication,
        @PathVariable projectId: String ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = validateProjectApiExceptionsAndIfValidatedReturnProject(projectId, authUser)
        val projectName = project.title

        taskService.deleteTasksFromProject(projectId)
        projectService.delete(project)
        return ResponseEntity.ok("Pomyślnie usunięto projekt '$projectName'")
    }

    @DeleteMapping
    fun deleteProjects(authentication: Authentication): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val projects = projectService.findProjectsByUserId(authUser.id)

        taskService.deleteTasksFromProjects(projects)
        projectService.deleteProjectsByUserId(authUser.id)
        return ResponseEntity.ok("Pomyślnie usunięto projekty")
    }

    private fun validateProjectApiExceptionsAndIfValidatedReturnProject(projectId: String, authUser: User): Project {
        val project = projectService.findProjectByIdAndUserId(projectId, authUser.id) ?: throw ApiException(404, "Szukany projekt nie istnieje")
        if (authUser.id != project.createdBy) throw ApiException(403, "To nie jest twój projekt")
        return project
    }
}