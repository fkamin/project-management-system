package home.projectmanagementsystem.dtos

import org.springframework.web.server.ResponseStatusException

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
    var description: String
)
data class TaskDto(
    var id: String?,
    var projectId: String?,
    var createdBy: String?,
    var title: String,
    var isCompleted: Boolean
)