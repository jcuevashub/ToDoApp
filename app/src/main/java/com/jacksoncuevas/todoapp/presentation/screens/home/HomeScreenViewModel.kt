package com.jacksoncuevas.todoapp.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksoncuevas.todoapp.domain.TaskLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskLocalDataSource: TaskLocalDataSource
) : ViewModel() {

    var state by mutableStateOf(
        HomeDataState()
    )
        private set

    private val eventChannel = Channel<HomeScreenEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        state = state.copy(
            date = LocalDate.now().let {
                DateTimeFormatter.ofPattern("EEE, MMMM dd yyyy").format(it)
            }
        )
        taskLocalDataSource.taskFlow
            .onEach {
                val completedTask = it.filter { task -> task.isCompleted }
                val pendingTask = it.filter { task -> !task.isCompleted }
                    .sortedByDescending { task ->
                        task.date
                    }

                state = state.copy(
                    summary = pendingTask.size.toString(),
                    completedTask = completedTask,
                    pendingTask = pendingTask
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when (action) {
                HomeScreenAction.OnDeleteAllTasks -> {
                    taskLocalDataSource.removeAllTasks()
                    eventChannel.send(HomeScreenEvent.DeletedAllTasks)
                }

                is HomeScreenAction.OnDeleteTask -> {
                    taskLocalDataSource.removeTask(action.task)
                    eventChannel.send(HomeScreenEvent.DeletedTask)
                }

                is HomeScreenAction.OnToggleTask -> {
                    val updatedTask = action.task.copy(isCompleted = !action.task.isCompleted)
                    taskLocalDataSource.updateTask(updatedTask)
                    eventChannel.send(HomeScreenEvent.UpdatedTasks)
                }

                else -> Unit

            }
        }
    }

}