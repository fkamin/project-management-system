package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.User
import home.projectmanagementsystem.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun findAllUsers(): MutableList<User> = userRepository.findAll()
    fun findUserById(id: String): User? = userRepository.findUserById(id)
    fun findByEmail(email: String): User? = userRepository.findByEmail(email)
    fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)
    fun save(user: User): User = userRepository.save(user)
    fun delete(user: User): Unit= userRepository.delete(user)
}
