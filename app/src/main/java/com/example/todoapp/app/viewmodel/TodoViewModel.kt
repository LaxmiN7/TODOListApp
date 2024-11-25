package com.example.todoapp.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.TodoItem
import com.example.todoapp.domain.usecases.AddTodoUseCase
import com.example.todoapp.domain.usecases.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setSearchText(newSearchText: String) {
        _searchText.value = newSearchText
    }

    // Set error message
    fun setError(errorMessage: String?) {
        _error.value = errorMessage
    }

    fun getTodos(query: String) {
        viewModelScope.launch {
            getTodosUseCase.execute(query).collect { todoList ->
                _todos.value = todoList
            }
        }
    }

    fun addTodo(todoItem: TodoItem) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addTodoUseCase.execute(todoItem)
                delay(3000)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Failed to add TODO"  // Set the error message
            }
        }
    }
}

