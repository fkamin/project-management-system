package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun findUserById(id: String): User? {
        return userRepository.findUserById(id)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun delete(user: User) {
        return userRepository.delete(user)
    }
}
