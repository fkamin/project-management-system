package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "tasks")
data class Task(
    @Id
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var deadline: Date = Date(),
    var state: String = "",
    var categories: MutableList<Category> = mutableListOf(),
    var projectId: String = ""
)
