package com.jacksoncuevas.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jacksoncuevas.todoapp.presentation.screens.detail.TaskScreenRoot
import com.jacksoncuevas.todoapp.presentation.screens.detail.TaskViewModel
import com.jacksoncuevas.todoapp.presentation.screens.home.HomeScreenRoot
import com.jacksoncuevas.todoapp.presentation.screens.home.HomeScreenViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeScreenDes
        ) {
            composable<HomeScreenDes> {
                val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

                HomeScreenRoot(
                    navigateToTaskScreen = { taskId ->
                        navController.navigate(TaskScreenDes(taskId = taskId))
                    },
                    viewModel = homeScreenViewModel
                )
            }

            composable<TaskScreenDes> {
                val taskViewModel = hiltViewModel<TaskViewModel>()

                TaskScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    viewModel = taskViewModel
                )
            }
        }
    }
}

@Serializable
object HomeScreenDes

@Serializable
data class TaskScreenDes(
    val taskId: String? = null
)
