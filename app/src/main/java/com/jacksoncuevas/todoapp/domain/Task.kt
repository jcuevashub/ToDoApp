package com.jacksoncuevas.todoapp.domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class Task @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean = false,
    val category: Category? = null,
    val date: LocalDateTime = LocalDateTime.now()
)