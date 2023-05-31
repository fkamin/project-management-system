package home.projectmanagementsystem.services

import home.projectmanagementsystem.dtos.ApiException
import home.projectmanagementsystem.models.Project
import home.projectmanagementsystem.repositories.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(private val projectRepository: ProjectRepository) {

    fun findProjectById(id: String): Project? = projectRepository.findProjectById(id)

    fun findProjectByIdAndUserId(projectId: String, userId: String?): Project? = projectRepository.findProjectByIdAndUserId(projectId, userId)

    fun findProjectsByUserId(userId: String?): MutableList<Project> = projectRepository.findProjectsByUserId(userId)

    fun save(project: Project): Project = projectRepository.save(project)

    fun updateTaskInProject(projectId: String, oldTaskId: String, newTaskId: String) {
        val project = findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        project.taskList.map { if (it == oldTaskId) newTaskId else it }
        save(project)
    }

    fun delete(project: Project) = projectRepository.delete(project)

    fun deleteTaskFromProject(projectId: String, taskId: String) {
        val project = findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        project.taskList.remove(taskId)
        save(project)
    }

    fun deleteTasksFromProject(projectId: String) {
        val project = findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        project.taskList.clear()
        save(project)
    }

    fun deleteProjectsByUserId(userId: String?) {
        val projects = findProjectsByUserId(userId)
        for (project in projects) {
            delete(project)
        }
    }
}