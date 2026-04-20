package com.example.a51.ui.theme

interface Repository {
    suspend fun getAll(): List<RepositoryItem>
    suspend fun addItem(title: String, text: String): List<RepositoryItem>
    suspend fun deleteItem(item: RepositoryItem): List<RepositoryItem>
    suspend fun getById(id: Long): RepositoryItem?
    suspend fun updateItem(id: Long, newTitle: String, newText: String): List<RepositoryItem>
}