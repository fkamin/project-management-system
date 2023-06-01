package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.CategoryDto
import home.projectmanagementsystem.dtos.TaskDto
import home.projectmanagementsystem.dtos.UserDto
import home.projectmanagementsystem.dtos.toDto
import home.projectmanagementsystem.services.CategoryService
import home.projectmanagementsystem.services.TaskService
import home.projectmanagementsystem.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val userService: UserService
) {
    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskDto> = taskService.findAllTasks().map { task -> task.toDto() }

    @GetMapping("/categories")
    fun getAllCategories(): List<CategoryDto> = categoryService.getAllCategories().map { category -> category.toDto() }

    @GetMapping("/users")
    fun getAllUsers(): List<UserDto> = userService.findAllUsers().map { user -> user.toDto() }
}