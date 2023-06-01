package home.projectmanagementsystem.dtos

import java.util.Date
data class RegisterDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
data class LoginDto(
    val email: String,
    val password: String
)
data class UpdateUserDto(
    val firstName: String,
    val lastName: String,
    val email: String
)
data class CreateProjectDto(
    val title: String,
    val description: String,
    val state: String
)
data class UpdateProjectDto(
    val title: String,
    val description: String,
    val state: String,
)
data class CreateTaskDto(
    val listOfCategories: MutableList<String>,
    val title: String,
    val description: String,
    val state: String
)
data class UpdateTaskDto(
    val listOfCategories: MutableList<String>,
    val title: String,
    val description: String,
    val state: String
)
data class CreateCategoryDto(
    val name: String
)
data class UpdateCategoryDto(
    val name: String
)
data class CreateCommentDto(
    val taskId: String,
    val createdBy: String,
    val content: String,
    val createdAt: Date,
    val wasEdited: Boolean
)
data class UpdateCommentDto(
    val content: String
)