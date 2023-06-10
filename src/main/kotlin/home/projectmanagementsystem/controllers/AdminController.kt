package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.TaskDto
import home.projectmanagementsystem.dtos.UserDto
import home.projectmanagementsystem.dtos.toDto
import home.projectmanagementsystem.services.TaskService
import home.projectmanagementsystem.services.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Bearer Authentication")
class AdminController(
    private val taskService: TaskService,
    private val userService: UserService
) {
    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskDto> = taskService.findAllTasks().map { task -> task.toDto() }

    @GetMapping("/users")
    fun getAllUsers(): List<UserDto> = userService.findAllUsers().map { user -> user.toDto() }
}