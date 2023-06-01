package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "comments")
data class Comment(
    @Id
    var id: String? = null,
    var taskId: String? = null,
    var createdBy: String? = null,
    var createdAt: Date = Date(),
    var content: String = "",
    var wasEdited: Boolean = false
)
