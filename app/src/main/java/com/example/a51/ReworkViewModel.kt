package com.example.a51

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a51.ui.theme.AddItemUseCase
import com.example.a51.ui.theme.DeleteItemUseCase
import com.example.a51.ui.theme.GetAllUseCase
import com.example.a51.ui.theme.GetByIdUseCase
import com.example.a51.ui.theme.UpdateItemUseCase
import com.example.a51.ui.theme.RepositoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ReworkViewModel(private val getAll: GetAllUseCase,
                      private val addItem: AddItemUseCase,
                      private val deleteItem: DeleteItemUseCase,
                      private val getById: GetByIdUseCase,
                      private val updateItemUseCase: UpdateItemUseCase): ViewModel() {
    private val _repos = MutableStateFlow<List<RepositoryItem>>(emptyList())
    val repos = _repos.asStateFlow()

    init {
        loadAll()
    }
    private fun loadAll() {
        viewModelScope.launch {
            _repos.value = getAll()
        }
    }

    fun addNewItem(title: String, text: String) {
        viewModelScope.launch {
            _repos.value = addItem(title, text)
        }
    }

    fun deletePrevItem(item: RepositoryItem) {
        viewModelScope.launch {
            _repos.value = deleteItem(item)
        }
    }

    suspend fun getItemById(id: Long): RepositoryItem? {
        return getById(id)
    }

    fun updateItem(id: Long, newTitle: String, newText: String) {
        viewModelScope.launch {
            _repos.value = updateItemUseCase(id, newTitle, newText)
        }
    }


    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }


}
