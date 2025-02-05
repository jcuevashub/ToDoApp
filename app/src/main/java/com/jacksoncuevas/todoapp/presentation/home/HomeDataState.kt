package com.jacksoncuevas.todoapp.presentation.home

import com.jacksoncuevas.todoapp.domain.Task

data class HomeDataState(
    val date: String = "",
    val summary: String = "",
    val completedTask: List<Task> = emptyList(),
    val pendingTask: List<Task> = emptyList(),
)