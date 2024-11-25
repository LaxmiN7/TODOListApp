package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.local.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    fun getTodos(query: String): Flow<List<TodoItem>> {
        return todoDao.getTodos("%$query%")
    }

    suspend fun addTodo(todoItem: TodoItem) {
        todoDao.insert(todoItem)
    }
}