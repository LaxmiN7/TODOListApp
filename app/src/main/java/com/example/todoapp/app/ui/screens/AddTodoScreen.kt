package com.example.todoapp.app.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.app.viewmodel.TodoViewModel
import com.example.todoapp.data.local.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(navController: NavController, viewModel: TodoViewModel = hiltViewModel()) {
    val title by viewModel.title.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    /*// Handle system back press
    BackHandler {
        // Set the flag to reset searchText on the parent screen (TodoListScreen)
        navController.previousBackStackEntry?.savedStateHandle?.set("resetSearchText", true)
        navController.popBackStack()
    }*/

    BackHandler {
        // Notify parent to reset searchText
        navController.previousBackStackEntry?.savedStateHandle?.set("resetSearchText", true)
        navController.popBackStack()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Enter New Item",
                        fontSize = 18.sp,  // Smaller text size
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.setTitle(it) },
                label = { Text("Enter TODO") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.lowercase().trim() == "error") {
                        // Simulate an error
                        viewModel.setError("Failed to add TODO")
                        return@Button
                    }

                    // Proceed with adding the TODO
                    viewModel.addTodo(TodoItem(title = title))
                    if (error == null) {
                        navController.popBackStack()  // Navigate back to TodoListScreen
                    }
                },
                /*modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),*/
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Add TODO", modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))  // Add space for loading indicator

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    error?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        viewModel.setError(null)  // Reset error after showing
    }
}

