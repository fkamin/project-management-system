package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tasks")
data class Task(
    @Id
    var id: String? = null,
    var projectId: String? = null,
    var createdBy: String? = null,
    var listOfCategories: MutableList<String>? = null,
    var title: String = "",
    var description: String = "",
    var state: String = ""
)
