package home.projectmanagementsystem.repositories

import home.projectmanagementsystem.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findUserById(id: String): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}