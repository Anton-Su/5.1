package com.example.a51

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.a51.ui.theme.AddItemUseCase
import com.example.a51.ui.theme.DataTxtSource
import com.example.a51.ui.theme.DeleteItemUseCase
import com.example.a51.ui.theme.GetAllUseCase
import com.example.a51.ui.theme.GetByIdUseCase
import com.example.a51.ui.theme.Navigation
import com.example.a51.ui.theme.RepositoryImpl
import com.example.a51.ui.theme.UpdateItemUseCase
import com.example.a51.ui.theme._51Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSource = DataTxtSource(applicationContext)
        val repository = RepositoryImpl(dataSource)
        val getTodosUseCase = GetAllUseCase(repository)
        val addTodoUseCase = AddItemUseCase(repository)
        val deleteTodoUseCase = DeleteItemUseCase(repository)
        val getByIdUseCase = GetByIdUseCase(repository)
        val updateItemUseCase = UpdateItemUseCase(repository)
        val viewModel = ReworkViewModel(getTodosUseCase, addTodoUseCase, deleteTodoUseCase, getByIdUseCase, updateItemUseCase)
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