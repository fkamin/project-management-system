package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.*


fun Project.toDto(): ProjectDto = ProjectDto(id, title, description, state, startDate, endDate, taskList, userId)

fun Task.toDto(): TaskDto = TaskDto(id, title, description, deadline, state, categories, projectId)

fun Category.toDto(): CategoryDto = CategoryDto(id, name, description)

fun Comment.toDto(): CommentDto = CommentDto(id, description, date, author, taskId, wasEdited)

fun User.toDto(): UserDto = UserDto(firstName, lastName, email)



