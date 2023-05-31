package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Category
import home.projectmanagementsystem.repositories.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun findById(id: String): Category? = categoryRepository.findById(id).orElseThrow()

    fun findByNameAndId(name: String, id: String): Category? = categoryRepository.findByNameAndId(name, id)

    fun getAllCategories(): List<Category> = categoryRepository.findAll()

    fun save(category: Category) = categoryRepository.save(category)

    fun delete(category: Category) = categoryRepository.delete(category)

}