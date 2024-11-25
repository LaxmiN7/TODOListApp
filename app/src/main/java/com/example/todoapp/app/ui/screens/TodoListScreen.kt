package com.example.todoapp.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.app.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(viewModel: TodoViewModel = hiltViewModel(), navController: NavController) {
    // Directly observe searchText from ViewModel
    val searchText = viewModel.searchText.collectAsState()

    // Save state of searchText across configuration changes
    val localSearchText = rememberSaveable { mutableStateOf(searchText.value) }

    // Fetch todos whenever searchText changes
    LaunchedEffect(localSearchText.value) {
        viewModel.getTodos(localSearchText.value)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("details")
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TextField bound to localSearchText state
            OutlinedTextField(
                value = localSearchText.value,
                onValueChange = {
                    localSearchText.value = it
                    viewModel.setSearchText(it)  // Sync with ViewModel
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.isLoading.collectAsState().value) {
                CircularProgressIndicator()
            } else if (viewModel.todos.collectAsState().value.isEmpty()) {
                Text("Press the + button to add a TODO item.")
            } else {
                viewModel.todos.collectAsState().value.forEach { todo ->
                    Text(todo.title)
                }
            }
        }
    }

    // Handle error display
    val error = viewModel.error.collectAsState().value
    error?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        viewModel.setError(null)
    }
}



