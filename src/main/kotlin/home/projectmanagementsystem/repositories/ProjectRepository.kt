package home.projectmanagementsystem.repositories

import home.projectmanagementsystem.models.Project
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, String> {
    fun findProjectsByCreatedBy(createdBy: String?): MutableList<Project>
    fun findProjectByIdAndCreatedBy(projectId: String?, createdBy: String?): Project?
    fun findProjectById(projectId: String): Project?
}