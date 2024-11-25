package com.example.todoapp.domain.usecases

import com.example.todoapp.data.local.TodoItem
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(private val todoRepository: TodoRepository) {

    fun execute(query: String): Flow<List<TodoItem>> {
        return todoRepository.getTodos(query)
    }
}