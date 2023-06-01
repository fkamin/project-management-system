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
    var createdBy: String?,
    var title: String,
    var description: String,
    var state: String
)
data class TaskDto(
    var id: String?,
    var projectId: String?,
    var createdBy: String?,
    var listOfCategories: MutableList<String>?,
    var title: String,
    var description: String,
    var state: String
)
data class CategoryDto(
    var id: String?,
    var name: String,
)
data class CommentDto(
    var id: String?,
    var taskId: String?,
    var createdBy: String?,
    var createdAt: Date,
    var content: String,
    var wasEdited: Boolean
)
