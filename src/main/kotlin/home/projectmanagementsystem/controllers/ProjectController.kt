package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Project
import home.projectmanagementsystem.services.ProjectService
import home.projectmanagementsystem.services.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

// TODO dorobic usuwanie z bazy (moze kaskadowe) komentarzy w przypadku usuwania projektu lub projektow

@RestController
@RequestMapping("/api/projects")
class ProjectController(
    private val projectService: ProjectService,
    private val taskService: TaskService) {

    @GetMapping("/{projectId}")
    fun getProject(authentication: Authentication, @PathVariable projectId: String): ProjectDto {
        val authUser = authentication.toUser()

        val project = projectService.findProjectByIdAndUserId(projectId, authUser.id) ?: throw ApiException(404, "Projekt nie znaleziony")

        return project.toDto()
    }

    @GetMapping
    fun getProjects(authentication: Authentication): List<ProjectDto> {
        val authUser = authentication.toUser()

        return projectService.findProjectsByUserId(authUser.id).map { project -> project.toDto() }
    }

    @PostMapping
    fun addProject(authentication: Authentication, @RequestBody payload: CreateProjectDto): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = Project(
            title = payload.title,
            description = payload.description,
            state = payload.state,
            startDate = payload.startDate,
            endDate = payload.endDate,
            taskList = payload.taskList,
            userId = authUser.id
        )

        projectService.save(project)
        return ResponseEntity.ok("Pomyślnie utworzono projekt: ${project.title}")
    }

    @PutMapping("/{projectId}")
    fun updateProject(
        authentication: Authentication,
        @RequestBody payload: UpdateProjectDto,
        @PathVariable projectId: String): ResponseEntity<String> {

        val authUser = authentication.toUser()
        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (authUser.id != project.userId) throw ApiException(403, "To nie jest twój projekt")

        project.title = payload.title
        project.description = payload.description
        project.state = payload.state
        project.startDate = payload.startDate
        project.endDate = payload.endDate

        projectService.save(project)
        return ResponseEntity.ok("Projekt pomyślnie zaktualizowano")
    }

    @DeleteMapping("/{projectId}")
    fun deleteProject(authentication: Authentication, @PathVariable projectId: String ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val project = projectService.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        if (authUser.id != project.userId) throw ApiException(403, "To nie jest twój projekt")

        taskService.deleteTasksFromProject(projectId)
        projectService.delete(project)

        return ResponseEntity.ok("Pomyślnie usunięto projekt")
    }

    @DeleteMapping
    fun deleteProjects(authentication: Authentication): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val projects = projectService.findProjectsByUserId(authUser.id)

        taskService.deleteTasksFromProjects(projects)
        projectService.deleteProjectsByUserId(authUser.id)

        return ResponseEntity.ok("Pomyślnie usunięto projekt")
    }
}