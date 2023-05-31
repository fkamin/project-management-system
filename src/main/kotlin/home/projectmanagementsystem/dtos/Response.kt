package home.projectmanagementsystem.dtos

import home.projectmanagementsystem.models.Category
import home.projectmanagementsystem.models.Task
import home.projectmanagementsystem.models.User
import org.springframework.web.server.ResponseStatusException
import java.util.*

class ApiException(code: Int, message: String): ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val token: String
)

data class TaskDto(
    var id: String?,
    var title: String,
    var description: String,
    var deadline: Date,
    var state: String,
    var categories: MutableList<Category>,
    var projectId: String
)

data class CategoryDto(
    var id: String?,
    var name: String,
    var description: String
)

data class ProjectDto(
    var id: String?,
    var title: String,
    var description: String,
    var state: String,
    var startDate: Date,
    var endDate: Date,
    var taskList: MutableList<String>,
    var userId: String?
)