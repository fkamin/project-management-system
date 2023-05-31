package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Category
import home.projectmanagementsystem.services.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): List<CategoryDto> = categoryService.getAllCategories().map { category -> category.toDto() }

    @PostMapping
    fun createCategory(@RequestBody payload: CreateCategoryDto): ResponseEntity<String> {
        val category = Category(
            name = payload.name,
            description = payload.description
        )

        categoryService.save(category)

        return ResponseEntity.ok("Pomyślnie dodano kategorie")
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(
        authentication: Authentication,
        @PathVariable categoryId: String,
        @RequestBody payload: UpdateCategoryDto): ResponseEntity<String> {
        val category = categoryService.findById(categoryId) ?: throw ApiException(404, "Kategoria nie została znaleziona")

        val existingCategory = categoryService.findByNameAndId(payload.name, categoryId)
        if ((existingCategory != null) && (existingCategory.id != categoryId)) throw ApiException(409, "Kategoria już istnieje")

        category.name = payload.name
        category.description = payload.description

        categoryService.save(category)
        return ResponseEntity.ok("Pomyślnie zaktualizowano kategorię")
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: String): ResponseEntity<String> {
        val category = categoryService.findById(categoryId) ?: throw ApiException(404, "Kategoria nie znaleziona")

        categoryService.delete(category)

        return ResponseEntity.ok("Pomyślnie usunięto kategorię")
    }
}