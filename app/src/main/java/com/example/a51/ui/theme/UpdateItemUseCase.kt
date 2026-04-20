package com.example.a51.ui.theme

class UpdateItemUseCase(private val repository: Repository) {
    suspend operator fun invoke(id: Long, newTitle: String, newText: String): List<RepositoryItem> {
        return repository.updateItem(id, newTitle, newText)
    }
}

