package com.example.domain.usecase

import com.example.data.model.BibleBookEntity
import com.example.data.repository.BibleReaderRepository
import kotlinx.coroutines.flow.Flow

class GetBibleBooksUseCase(
    private val repository: BibleReaderRepository
) {
    suspend fun initialize() {
        repository.initializeCatalogIfNeeded()
    }

    operator fun invoke(): Flow<List<BibleBookEntity>> {
        return repository.getAllBooks()
    }
}
