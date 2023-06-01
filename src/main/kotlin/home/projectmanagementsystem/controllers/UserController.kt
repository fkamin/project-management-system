package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.services.CommentService
import home.projectmanagementsystem.services.ProjectService
import home.projectmanagementsystem.services.TaskService
import home.projectmanagementsystem.services.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

// TODO dorobic usuwanie z bazy (moze kaskadowe) projektow, taskow i komentarzy w przypadku usuwania usera

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
class UserController(
    private val userService: UserService,
    private val projectService: ProjectService,
    private val taskService: TaskService,
    private val commentService: CommentService) {

    @GetMapping("/{userId}")
    fun getUser(authentication: Authentication, @PathVariable userId: String): UserDto {
        val authUser = authentication.toUser()

        val user = validateUserApiExceptionsAndIfValidatedReturnUser(userId, authUser)

        return user.toDto()
    }

    @PutMapping("/{userId}")
    fun updateUser(
        authentication: Authentication,
        @PathVariable userId: String,
        @RequestBody payload: UpdateUserDto
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val user = validateUserApiExceptionsAndIfValidatedReturnUser(userId, authUser)

        if (userService.existsByEmail(payload.email)) throw ApiException(400, "Email jest zajęty")

        user.firstName = payload.firstName
        user.lastName = payload.lastName
        user.email = payload.email

        userService.save(user)
        return ResponseEntity.ok("Pomyślnie zaktualizowano użytkownika")
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        authentication: Authentication,
        @PathVariable userId: String
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val user = validateUserApiExceptionsAndIfValidatedReturnUser(userId, authUser)

        commentService.deleteCommentsByUserId(userId)
        taskService.deleteTasksByUserId(userId)
        projectService.deleteProjectsByUserId(userId)
        userService.delete(user)
        return ResponseEntity.ok("Pomyślnie usunięto użytkownika")
    }

    private fun validateUserApiExceptionsAndIfValidatedReturnUser(
        userId: String,
        authUser: User
    ): User {
        val user = userService.findUserById(userId) ?: throw ApiException(404, "Użytkownik nie istnieje")
        if (userId != authUser.id) throw ApiException(404, "Nie masz dostępu do tego użytkownika")
        return user
    }

}