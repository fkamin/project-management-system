package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.*

fun Task.toDto(): TaskDto {
    return TaskDto(id, title, description, deadline, state, categories, projectId)
}

fun Category.toDto(): CategoryDto {
    return CategoryDto(id, name, description)
}

fun Project.toDto(): ProjectDto {
    return ProjectDto(id, title, description, state, startDate, endDate, taskList, userId)
}