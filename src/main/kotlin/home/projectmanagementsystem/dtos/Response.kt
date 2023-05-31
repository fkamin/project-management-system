package home.projectmanagementsystem.dtos

import org.springframework.web.server.ResponseStatusException
import java.util.*

class ApiException(code: Int, message: String): ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val token: String
)

data class UserDto(
    var firstName: String,
    var lastName: String,
    var email: String,
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

data class TaskDto(
    var id: String?,
    var title: String,
    var description: String,
    var deadline: Date,
    var state: String,
    var categories: MutableList<String>,
    var projectId: String
)

data class CategoryDto(
    var id: String?,
    var name: String,
    var description: String
)

data class CommentDto(
    var id: String?,
    var description: String,
    var date: Date,
    var author: String?,
    var taskId: String?,
    var wasEdited: Boolean
)
