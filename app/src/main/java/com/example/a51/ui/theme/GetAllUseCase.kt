package com.example.a51.ui.theme

class GetAllUseCase(private val repository: Repository) {
    suspend operator fun invoke(): List<RepositoryItem> {
        return repository.getAll()
    }
}