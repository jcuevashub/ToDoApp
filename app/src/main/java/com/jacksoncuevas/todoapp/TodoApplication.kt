package com.jacksoncuevas.todoapp

import android.app.Application
import com.jacksoncuevas.todoapp.data.DataSourceFactory
import com.jacksoncuevas.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TodoApplication: Application() {
    val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO

    val dataSource: TaskLocalDataSource
        get() = DataSourceFactory.createDataSource(this, dispatcherIO)
}