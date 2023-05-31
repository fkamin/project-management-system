package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.ApiException
import home.projectmanagementsystem.dtos.LoginDto
import home.projectmanagementsystem.dtos.LoginResponseDto
import home.projectmanagementsystem.dtos.RegisterDto
import home.projectmanagementsystem.services.HashService
import home.projectmanagementsystem.services.TokenService
import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(@RequestBody payload: LoginDto): LoginResponseDto {
        val user = userService.findByEmail(payload.email) ?: throw ApiException(400, "Błąd podczas logowania")

        if (!hashService.checkBcrypt(payload.password, user.password)) {
            throw ApiException(400, "Błąd podczas logowania")
        }

        return LoginResponseDto(
            token = tokenService.createToken(user)
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (userService.existsByEmail(payload.email)) {
            throw ApiException(400, "Email już istnieje")
        }

        val user = User(
            firstName = payload.firstName,
            lastName = payload.lastName,
            email = payload.email,
            password = hashService.hashBcrypt(payload.password)
        )

        val savedUser = userService.save(user)

        return LoginResponseDto(
            token = tokenService.createToken(savedUser)
        )
    }
}