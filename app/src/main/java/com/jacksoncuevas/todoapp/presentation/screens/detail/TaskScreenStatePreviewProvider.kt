package com.jacksoncuevas.todoapp.presentation.screens.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jacksoncuevas.todoapp.domain.Category

class TaskScreenStatePreviewProvider: PreviewParameterProvider<TaskScreenState> {
    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                taskName = "Task 1",
                taskDescription = "Description 1",
                isTaskDone = false,
                category = Category.WORK
            )
        )
}