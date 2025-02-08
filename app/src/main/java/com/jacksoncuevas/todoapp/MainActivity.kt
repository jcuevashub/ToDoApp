package com.jacksoncuevas.todoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.jacksoncuevas.todoapp.navigation.NavigationRoot
import com.jacksoncuevas.todoapp.presentation.screens.detail.TaskScreenRoot
import com.jacksoncuevas.todoapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavigationRoot(
                    navController = navController
                )
            }
        }
    }
}