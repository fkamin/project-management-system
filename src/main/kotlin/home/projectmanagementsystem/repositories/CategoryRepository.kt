package home.projectmanagementsystem.repositories

import home.projectmanagementsystem.models.Category
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepository : MongoRepository<Category, String> {
    fun findByNameAndId(name: String, id: String): Category?
}