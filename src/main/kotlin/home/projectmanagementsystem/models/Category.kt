package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "categories")
data class Category(
    @Id
    var id: String? = null,
    var name: String = ""
)