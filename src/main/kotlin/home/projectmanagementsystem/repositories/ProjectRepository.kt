package home.projectmanagementsystem.repositories

import home.projectmanagementsystem.models.Project
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, String> {
    fun findProjectsByUserId(userId: String?): MutableList<Project>
    fun findProjectByUserId(userId: String?): Project
    fun findProjectById(projectId: String): Project
}