package com.jacksoncuevas.todoapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksoncuevas.todoapp.data.FakeTaskLocalDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val taskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(
        HomeDataState()
    )
        private set

    private val eventChannel = Channel<HomeScreenEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        taskLocalDataSource.taskFlow
            .onEach {
                val completedTask = it.filter { task -> task.isCompleted }
                val pendingTask = it.filter { task -> !task.isCompleted }

                state = state.copy(
                    date = "March 9, 2024",
                    summary = "${pendingTask.size} incomplete, ${completedTask.size} completed",
                    completedTask = completedTask,
                    pendingTask = pendingTask
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when (action) {
                HomeScreenAction.OnDeleteAllTasks -> {
                    taskLocalDataSource.deleteAllTasks()
                    eventChannel.send(HomeScreenEvent.DeletedAllTasks)
                }

                is HomeScreenAction.OnDeleteTask -> {
                    taskLocalDataSource.removeTask(action.task)
                    eventChannel.send(HomeScreenEvent.DeletedTask)
                }

                is HomeScreenAction.OnToggleTask -> {
                    val updatedTask = action.task.copy(isCompleted = !action.task.isCompleted)
                    taskLocalDataSource.updateTask(updatedTask)
                }

                else -> Unit

            }
        }
    }

}