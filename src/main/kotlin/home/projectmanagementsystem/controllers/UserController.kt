package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.configs.toUser
import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

// TODO dorobic usuwanie z bazy (moze kaskadowe) projektow, taskow i komentarzy w przypadku usuwania usera

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{userId}")
    fun getUser(authentication: Authentication, @PathVariable userId: String): UserDto {
        val authUser = authentication.toUser()

        val user = userService.findUserById(userId) ?: throw ApiException(404, "User nie istnieje")
        if (userId != authUser.id) throw ApiException(404, "Nie masz dostępu do tego usera")

        return user.toDto()
    }

    @PutMapping("/{userId}")
    fun updateUser(
        authentication: Authentication,
        @PathVariable userId: String,
        @RequestBody payload: UpdateUserDto
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val user = userService.findUserById(userId) ?: throw ApiException(404, "User nie istnieje")
        if (userId != authUser.id) throw ApiException(404, "Nie masz dostępu do tego usera")

        // TODO sprawdzic czy istnieje email w bazie, jezeli tak to nie dodajemy

        user.firstName = payload.firstName
        user.lastName = payload.lastName
        user.email = payload.email

        userService.save(user)
        return ResponseEntity.ok("Pomyślnie zedytowano usera")
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        authentication: Authentication,
        @PathVariable userId: String
    ): ResponseEntity<String> {
        val authUser = authentication.toUser()

        val user = userService.findUserById(userId) ?: throw ApiException(404, "User nie istnieje")
        if (userId != authUser.id) throw ApiException(404, "Nie masz dostępu do tego usera")

        userService.delete(user)
        return ResponseEntity.ok("Pomyślnie usunięto usera")
    }

}