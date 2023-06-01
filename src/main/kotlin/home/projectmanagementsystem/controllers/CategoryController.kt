package home.projectmanagementsystem.controllers

import home.projectmanagementsystem.dtos.*
import home.projectmanagementsystem.models.Category
import home.projectmanagementsystem.services.CategoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "Bearer Authentication")
class CategoryController(private val categoryService: CategoryService) {
    @GetMapping
    fun getAllCategories(): List<CategoryDto> = categoryService.getAllCategories().map { category -> category.toDto() }

    @PostMapping
    fun createCategory(@RequestBody payload: CreateCategoryDto): ResponseEntity<String> {
        if (categoryService.existsByName(payload.name)) throw ApiException(409, "Kategoria ${payload.name} już istnieje")

        categoryService.save(Category(name = payload.name))
        return ResponseEntity.ok("Pomyślnie dodano kategorię ${payload.name}")
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: String,
        @RequestBody payload: UpdateCategoryDto): ResponseEntity<String> {
        val category = validateCategoryApiExceptionsAndIfValidatedReturnCategory(categoryId, payload.name)

        category.name = payload.name

        categoryService.save(category)
        return ResponseEntity.ok("Pomyślnie zaktualizowano kategorię '${payload.name}'")
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: String): ResponseEntity<String> {
        val category = categoryService.findById(categoryId) ?: throw ApiException(404, "Szukana kategoria nie istnieje")
        val categoryName = category.name

        categoryService.delete(category)
        return ResponseEntity.ok("Pomyślnie usunięto kategorię '$categoryName'")
    }

    fun validateCategoryApiExceptionsAndIfValidatedReturnCategory(categoryId: String, name: String): Category {
        val category = categoryService.findById(categoryId) ?: throw ApiException(404, "Szukana kategoria nie istnieje")
        if (categoryService.existsByName(name)) throw ApiException(409, "Kategoria '$name' już istnieje")
        return category
    }
}