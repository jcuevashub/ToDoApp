package com.jacksoncuevas.todoapp.data.di

import android.content.Context
import androidx.room.Room
import com.jacksoncuevas.todoapp.data.RoomTaskLocalDataSource
import com.jacksoncuevas.todoapp.data.TaskDao
import com.jacksoncuevas.todoapp.data.TodoDatabase
import com.jacksoncuevas.todoapp.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext
        context: Context
    ) : TodoDatabase {
        return  Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TodoDatabase
    ): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        taskDao: TaskDao,
        @Named("dispatcherIO")
        dispatcherIO: CoroutineDispatcher
    ) : TaskLocalDataSource {
        return RoomTaskLocalDataSource(taskDao,dispatcherIO)
    }
}