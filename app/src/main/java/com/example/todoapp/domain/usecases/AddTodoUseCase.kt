package com.example.todoapp.domain.usecases

import com.example.todoapp.data.local.TodoItem
import com.example.todoapp.data.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {

    suspend fun execute(todoItem: TodoItem) {
        todoRepository.addTodo(todoItem)
    }
}