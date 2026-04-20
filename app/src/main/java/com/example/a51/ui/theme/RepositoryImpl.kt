package com.example.a51.ui.theme

import kotlinx.coroutines.delay

class RepositoryImpl(private val dataSource: DataTxtSource): Repository {
    private var repos: MutableList<RepositoryItem>? = null
    private suspend fun ensureLoaded() {
        if (repos == null) {
            val list = dataSource.getRepos()
            repos = list.toMutableList()
        }
    }

    override suspend fun getAll(): List<RepositoryItem> {
        ensureLoaded()
        delay(1000)
        return repos ?: emptyList()
    }

    override suspend fun addItem(title: String, text: String): List<RepositoryItem> {
        ensureLoaded()
        val newRepositoryItem = dataSource.saveItem(title, text)
        val updated = repos ?: mutableListOf()
        updated.add(0, newRepositoryItem) // add to front so sorted desc
        repos = updated
        return repos ?: emptyList()
    }

    override suspend fun deleteItem(item: RepositoryItem): List<RepositoryItem> {
        ensureLoaded()
        repos = repos?.filter { it != item }?.toMutableList()
        return repos ?: emptyList()
    }

    override suspend fun getById(id: Long): RepositoryItem? {
        ensureLoaded()
        // Try to find in memory first
        val found = repos?.firstOrNull { it.timestamp == id }
        if (found != null) return found
        // fallback to disk
        return dataSource.getById(id)
    }

    override suspend fun updateItem(id: Long, newTitle: String, newText: String): List<RepositoryItem> {
        ensureLoaded()
        val updatedItem = dataSource.updateItem(id, newTitle, newText)
        repos = repos?.map {
            if (it.timestamp == id) updatedItem ?: it else it
        }?.toMutableList() ?: updatedItem?.let { mutableListOf(it) }
        repos = repos?.sortedByDescending { it.timestamp }?.toMutableList()
        return repos ?: emptyList()
    }
}