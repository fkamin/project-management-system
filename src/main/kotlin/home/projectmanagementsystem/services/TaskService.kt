package home.projectmanagementsystem.services

import home.projectmanagementsystem.dtos.ApiException
import home.projectmanagementsystem.models.Project
import home.projectmanagementsystem.models.Task
import home.projectmanagementsystem.repositories.ProjectRepository
import home.projectmanagementsystem.repositories.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository) {

    fun findTaskById(taskId: String): Task? {
        return taskRepository.findTaskById(taskId)
    }

    fun findTaskByIdAndProjectId(taskId: String, projectId: String): Task? {
        return taskRepository.findTaskByIdAndProjectId(taskId, projectId)
    }

    fun findTasksByProjectId(projectId: String): MutableList<Task> {
        return taskRepository.findTasksByProjectId(projectId)
    }

    fun findAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    fun save(task: Task): Task {
        return taskRepository.save(task)
    }

    fun addTaskToProject(projectId: String, task: Task): Project {
        val project = projectRepository.findProjectById(projectId) ?: throw ApiException(404, "Projekt nie istnieje")
        project.taskList.add(task.id.toString())
        save(task)
        return projectRepository.save(project)
    }

    fun updateCommentInTask(taskId: String, oldCommentId: String, newCommentId: String) {
        val task = findTaskById(taskId) ?: throw ApiException(404, "Task nie istnieje")
        task.comments.map { if (it == oldCommentId) newCommentId else it }
        save(task)
    }

    fun delete(task: Task) {
        taskRepository.delete(task)
    }

    fun deleteTasks(tasks: MutableList<Task>) {
        for (task in tasks) delete(task)
    }

    fun deleteTasksFromProject(projectId: String) {
        val tasks = findTasksByProjectId(projectId)
        deleteTasks(tasks)
    }

    fun deleteTasksFromProjects(projects: MutableList<Project>) {
        for (project in projects) {
            deleteTasksFromProject(project.id.toString())
        }
    }



}