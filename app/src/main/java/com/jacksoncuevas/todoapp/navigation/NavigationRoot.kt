package com.jacksoncuevas.todoapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jacksoncuevas.todoapp.presentation.screens.detail.TaskScreenRoot
import com.jacksoncuevas.todoapp.presentation.screens.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
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
                HomeScreenRoot(
                    navigateToTaskScreen = {
                        navController.navigate(TaskScreenDes)
                    }
                )
            }

            composable<TaskScreenDes> {
                TaskScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Serializable
object HomeScreenDes

@Serializable
object TaskScreenDes
