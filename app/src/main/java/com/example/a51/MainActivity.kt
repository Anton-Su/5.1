package com.example.a51

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a51.ui.theme.AddItemUseCase
import com.example.a51.ui.theme.DataTxtSource
import com.example.a51.ui.theme.DeleteItemUseCase
import com.example.a51.ui.theme.GetAllUseCase
import com.example.a51.ui.theme.RepositoryImpl
import com.example.a51.ui.theme._51Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.toString
import androidx.navigation.NavHostController
import com.example.a51.ui.theme.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSource = DataTxtSource(applicationContext)
        val repository = RepositoryImpl(dataSource)
        val getTodosUseCase = GetAllUseCase(repository)
        val addTodoUseCase = AddItemUseCase(repository)
        val deleteTodoUseCase = DeleteItemUseCase(repository)
        val viewModel = ReworkViewModel(getTodosUseCase, addTodoUseCase, deleteTodoUseCase)
        enableEdgeToEdge()
        setContent {
            _51Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    _51Theme {
//        Greeting("Android")
//    }
//}