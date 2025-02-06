package com.jacksoncuevas.todoapp.presentation.screens.home

import com.jacksoncuevas.todoapp.domain.Task

sealed interface HomeScreenAction {
    data class OnToggleTask(val task: Task) : HomeScreenAction
    data class OnDeleteTask(val task: Task) : HomeScreenAction
    data object OnDeleteAllTasks : HomeScreenAction
    data object OnAddTask : HomeScreenAction
}