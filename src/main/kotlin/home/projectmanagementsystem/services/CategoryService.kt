package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Category
import home.projectmanagementsystem.repositories.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun findById(id: String): Category? = categoryRepository.findById(id).orElseThrow()
    fun existsByName(name: String): Boolean = categoryRepository.existsByName(name)
    fun getAllCategories(): List<Category> = categoryRepository.findAll()
    fun save(category: Category): Category = categoryRepository.save(category)
    fun delete(category: Category): Unit = categoryRepository.delete(category)

}