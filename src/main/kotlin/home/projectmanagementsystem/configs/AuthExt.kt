package home.projectmanagementsystem.configs

import home.projectmanagementsystem.models.User
import org.springframework.security.core.Authentication

fun Authentication.toUser(): User {
    return principal as User
}