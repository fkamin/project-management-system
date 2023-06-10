package home.projectmanagementsystem.dtos

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
data class UpdateUserPasswordDto(
    val currentPassword: String,
    val newPassword: String
)

data class CreateProjectDto(
    val title: String,
    val description: String,
)
data class UpdateProjectDto(
    val title: String,
    val description: String,
)

data class CreateTaskDto(
    val title: String
)
data class UpdateTaskDto(
    val title: String,
    val isCompleted: Boolean
)