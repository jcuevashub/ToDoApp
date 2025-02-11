package com.jacksoncuevas.todoapp.domain

import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    val taskFlow: Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun removeTask(task: Task)
    suspend fun getTaskById(id: String): Task?
    suspend fun removeAllTasks()
}