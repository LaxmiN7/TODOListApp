package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.usecases.AddTodoUseCase
import com.example.todoapp.domain.usecases.GetTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "todo.db").build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: AppDatabase) = database.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao) = TodoRepository(todoDao)

    @Provides
    @Singleton
    fun provideGetTodosUseCase(todoRepository: TodoRepository) = GetTodosUseCase(todoRepository)

    @Provides
    @Singleton
    fun provideAddTodoUseCase(todoRepository: TodoRepository) = AddTodoUseCase(todoRepository)
}