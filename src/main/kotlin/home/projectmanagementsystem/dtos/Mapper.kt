package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.*

fun User.toDto(): UserDto = UserDto(firstName, lastName, email)
fun Project.toDto(): ProjectDto = ProjectDto(id, createdBy, title, description)
fun Task.toDto(): TaskDto = TaskDto(id, projectId, createdBy, title, isCompleted)
