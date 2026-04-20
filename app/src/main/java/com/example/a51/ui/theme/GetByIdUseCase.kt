package com.example.a51.ui.theme

class GetByIdUseCase(private val repository: Repository) {
    suspend operator fun invoke(id: Long): RepositoryItem? {
        return repository.getById(id)
    }
}

