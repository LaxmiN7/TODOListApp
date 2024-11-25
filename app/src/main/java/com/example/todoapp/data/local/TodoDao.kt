package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoItem: TodoItem)

    @Query("SELECT * FROM todos WHERE title LIKE :query")
    fun getTodos(query: String): Flow<List<TodoItem>>

}