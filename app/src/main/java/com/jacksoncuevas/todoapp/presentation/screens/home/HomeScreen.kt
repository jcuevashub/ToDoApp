@file:OptIn(ExperimentalFoundationApi::class)

package com.jacksoncuevas.todoapp.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jacksoncuevas.todoapp.presentation.screens.home.providers.HomeScreenPreviewProvider
import com.jacksoncuevas.todoapp.ui.theme.AppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jacksoncuevas.todoapp.R

@Composable
fun HomeScreenRoot() {
    val viewModel = viewModel<HomeScreenViewModel>()
    val state = viewModel.state
    val event = viewModel.event

    val context = LocalContext.current

    LaunchedEffect(true) {
        event.collect { event ->
            when(event) {
                HomeScreenEvent.DeletedAllTasks -> {
                    Toast.makeText(context, context.getString(R.string.all_tasks_deleted), Toast.LENGTH_SHORT)
                }
                HomeScreenEvent.DeletedTask -> {
                    Toast.makeText(context, context.getString(R.string.task_deleted), Toast.LENGTH_SHORT)

                }
                HomeScreenEvent.UpdatedTasks -> TODO()
            }
        }
    }
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeDataState,
    onAction: (HomeScreenAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                isMenuExpanded = true
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add Task",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    onAction(HomeScreenAction.OnDeleteAllTasks)
                                    isMenuExpanded = false
                                },
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.delete_all)
                                    )
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Add Task",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                SummaryInfo(
                    date = state.date,
                    taskSummary = state.summary,
                    completedTask = state.completedTask.size,
                    totalTask = state.completedTask.size + state.pendingTask.size
                )
            }

            stickyHeader {
                SectionTitle(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface),
                    title = stringResource(R.string.completed_taks)
                )
            }

            items(
                state.completedTask,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    modifier = Modifier.clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                    task = task,
                    onClickItem = {},
                    onDeleteItem = {
                        onAction(HomeScreenAction.OnDeleteTask(task))
                    },
                    onToggleCompletion = {
                        onAction(HomeScreenAction.OnDeleteTask(task))
                    }
                )

            }

            stickyHeader {
                SectionTitle(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface),
                    title = stringResource(R.string.pending_tasks)
                )
            }

            items(
                state.pendingTask,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    modifier = Modifier.clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                    task = task,
                    onClickItem = {},
                    onDeleteItem = {
                        onAction(HomeScreenAction.OnDeleteTask(task))
                    },
                    onToggleCompletion = {
                        onAction(HomeScreenAction.OnToggleTask(task))
                    }
                )

            }


        }
    }
}

@Preview
@Composable
fun HomeScreenPreviewLight(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    MaterialTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask = state.pendingTask,

                ),
            onAction = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    AppTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask = state.pendingTask
            ),
            onAction = {}
        )
    }
}