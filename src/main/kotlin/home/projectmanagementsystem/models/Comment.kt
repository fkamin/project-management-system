package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "comments")
data class Comment(
    @Id
    var id: String? = null,
    var description: String = "",
    var author: User = User(),
    var date: Date = Date(),
    var taskId: String = ""
)
