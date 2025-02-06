package com.jacksoncuevas.todoapp.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jacksoncuevas.todoapp.domain.Category
import com.jacksoncuevas.todoapp.domain.Task
import com.jacksoncuevas.todoapp.presentation.screens.home.HomeDataState

class HomeScreenPreviewProvider: PreviewParameterProvider<HomeDataState> {
    override val values: Sequence<HomeDataState>
        get() = sequenceOf(
            HomeDataState(
                date = "March 9, 2024",
                summary = "5 incompleted, 5 completed",
                completedTask = completedTask,
                pendingTask = pendingTask

            )
        )
}

val completedTask = mutableListOf<Task>()
    .apply {
        repeat(20) {
            add(
                Task(
                    id = it.toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.OTHER,
                    isCompleted = false
                )
            )
        }
    }

val pendingTask = mutableListOf<Task>()
    .apply {
        repeat(20) {
            add(
                Task(
                    id = (it+30).toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.OTHER,
                    isCompleted = false
                )
            )
        }
    }