package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.Category
import java.util.Date

data class LoginDto(
    val email: String,
    val password: String
)

data class RegisterDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class CreateTaskDto(
    val title: String,
    val description: String,
    val deadline: Date,
    val state: String,
    val categories: MutableList<Category>
)

data class UpdateTaskDto(
    val title: String,
    val description: String,
    val deadline: Date,
    val state: String,
    val categories: MutableList<Category>
)

data class CreateCategoryDto(
    val name: String,
    val description: String
)

data class UpdateCategoryDto(
    var id: String,
    val name: String,
    val description: String
)

data class CreateProjectDto(
    var title: String,
    var description: String,
    var state: String,
    var startDate: Date = Date(),
    var endDate: Date = Date(),
    var taskList: MutableList<String>
)

data class UpdateProjectDto(
    var title: String,
    var description: String,
    var state: String,
    var startDate: Date = Date(),
    var endDate: Date = Date(),
)