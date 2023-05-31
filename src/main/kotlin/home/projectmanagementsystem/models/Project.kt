package home.projectmanagementsystem.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "projects")
data class Project(
    @Id
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var state: String = "",
    var startDate: Date = Date(),
    var endDate: Date = Date(),
    var taskList: MutableList<String> = mutableListOf(),
    var userId: String? = ""
)
