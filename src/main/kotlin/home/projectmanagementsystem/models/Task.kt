package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tasks")
data class Task(
    @Id
    var id: String? = null,
    var projectId: String? = null,
    var createdBy: String? = null,
    var title: String = "",
    var isCompleted: Boolean = false
)
