package com.jacksoncuevas.todoapp.presentation.home

sealed class HomeScreenEvent {
    data object UpdatedTasks : HomeScreenEvent()
    data object DeletedAllTasks : HomeScreenEvent()
    data object DeletedTask : HomeScreenEvent()
}