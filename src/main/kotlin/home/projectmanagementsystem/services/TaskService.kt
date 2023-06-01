package home.projectmanagementsystem.services

import home.projectmanagementsystem.models.Project
import home.projectmanagementsystem.models.Task
import home.projectmanagementsystem.repositories.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun findTaskByIdAndProjectId(taskId: String, projectId: String): Task? = taskRepository.findTaskByIdAndProjectId(taskId, projectId)
    fun findTasksByProjectId(projectId: String?): MutableList<Task> = taskRepository.findTasksByProjectId(projectId)
    fun findTasksByUserId(userId: String): MutableList<Task> = taskRepository.findTasksByCreatedBy(userId)
    fun findAllTasks(): List<Task> = taskRepository.findAll()
    fun save(task: Task): Task = taskRepository.save(task)
    fun delete(task: Task): Unit = taskRepository.delete(task)
    fun deleteTasksFromProject(projectId: String?) {
        val tasks = findTasksByProjectId(projectId)
        for (task in tasks) delete(task)
    }
    fun deleteTasksFromProjects(projects: MutableList<Project>) {
        for (project in projects) deleteTasksFromProject(project.id)
    }
    fun deleteTasksByUserId(userId: String) {
        val tasks = findTasksByUserId(userId)
        for (task in tasks) delete(task)
    }
}