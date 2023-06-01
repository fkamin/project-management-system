package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Project
import home.projectmanagementsystem.repositories.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(private val projectRepository: ProjectRepository) {
    fun findProjectById(id: String): Project? = projectRepository.findProjectById(id)
    fun findProjectByIdAndUserId(projectId: String, userId: String?): Project? = projectRepository.findProjectByIdAndCreatedBy(projectId, userId)
    fun findProjectsByUserId(userId: String?): MutableList<Project> = projectRepository.findProjectsByCreatedBy(userId)
    fun save(project: Project): Project = projectRepository.save(project)
    fun delete(project: Project): Unit = projectRepository.delete(project)
    fun deleteProjectsByUserId(userId: String?) {
        val projects = findProjectsByUserId(userId)
        for (project in projects) delete(project)
    }
}