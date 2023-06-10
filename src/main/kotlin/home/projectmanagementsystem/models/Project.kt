package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "projects")
data class Project(
    @Id
    var id: String? = null,
    var createdBy: String? = null,
    var title: String = "",
    var description: String = ""
)
