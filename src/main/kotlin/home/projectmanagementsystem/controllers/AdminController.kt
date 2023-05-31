package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.TaskDto
import home.projectmanagementsystem.dtos.toDto
import home.projectmanagementsystem.services.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val taskService: TaskService
) {
    @GetMapping("/tasks")
    fun getAllTasksByAdmin(): List<TaskDto> = taskService.findAllTasks().map { task -> task.toDto() }
}