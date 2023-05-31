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
data class CreateProjectDto(
    var title: String,
    var description: String,
    var state: String,
    var startDate: Date,
    var endDate: Date,
    var taskList: MutableList<String>
)
data class UpdateProjectDto(
    var title: String,
    var description: String,
    var state: String,
    var startDate: Date,
    var endDate: Date,
)
data class CreateTaskDto(
    val title: String,
    val description: String,
    val deadline: Date,
    val state: String,
    val categories: MutableList<String>
)
data class UpdateTaskDto(
    val title: String,
    val description: String,
    val deadline: Date,
    val state: String,
    val categories: MutableList<String>
)
data class CreateCategoryDto(
    val name: String,
    val description: String
)

data class UpdateCategoryDto(
    val name: String,
    val description: String
)
data class CreateCommentDto(
    val description: String,
    val date: Date
)
data class UpdateCommentDto(
    val description: String,
    val date: Date
)

data class UpdateUserDto(
    val firstName: String,
    val lastName: String,
    val email: String
)