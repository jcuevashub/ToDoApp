package com.jacksoncuevas.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jacksoncuevas.todoapp.data.FakeTaskLocalDataSource
import com.jacksoncuevas.todoapp.domain.Task
import com.jacksoncuevas.todoapp.ui.theme.AppTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text by remember {
                mutableStateOf("")
            }

            val fakeLocalDataSource = FakeTaskLocalDataSource

            LaunchedEffect(true) {
                fakeLocalDataSource.taskFlow.collect {
                    text = it.toString()
                }
            }

            LaunchedEffect(true) {
                fakeLocalDataSource.addTask(
                    Task(
                        id = UUID.randomUUID().toString(),
                        title = "Task 1",
                        description = "Description 1"
                    )
                )
            }
            Scaffold(modifier = Modifier.fillMaxSize()) { it ->
                Text(text = text, modifier = Modifier.fillMaxSize())
            }

//            AppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting(text)
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}