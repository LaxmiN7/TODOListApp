package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.app.ui.screens.AddTodoScreen
import com.example.todoapp.app.ui.screens.TodoListScreen
import com.example.todoapp.ui.theme.TODOListAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODOListAppTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") { TodoListScreen(navController = navController) }
        composable("details") { AddTodoScreen(navController = navController) }
    }
}