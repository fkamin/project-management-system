package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.*

fun Project.toDto(): ProjectDto = ProjectDto(id, createdBy, title, description, state)
fun Task.toDto(): TaskDto = TaskDto(id, projectId, createdBy, listOfCategories, title, description, state)
fun Category.toDto(): CategoryDto = CategoryDto(id, name)
fun Comment.toDto(): CommentDto = CommentDto(id, taskId, createdBy, createdAt, content, wasEdited)
fun User.toDto(): UserDto = UserDto(firstName, lastName, email)



