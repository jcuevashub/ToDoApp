package com.jacksoncuevas.todoapp.presentation.screens.detail.providers

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jacksoncuevas.todoapp.domain.Category
import com.jacksoncuevas.todoapp.presentation.screens.detail.TaskScreenState

class TaskScreenStatePreviewProvider: PreviewParameterProvider<TaskScreenState> {
    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                taskName = TextFieldState("Task 1"),
                taskDescription = TextFieldState("Description 1"),
                isTaskDone = false,
                category = Category.WORK
            )
        )
}