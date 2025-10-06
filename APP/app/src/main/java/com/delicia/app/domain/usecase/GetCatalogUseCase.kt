package com.delicia.app.domain.usecase

import com.delicia.app.core.Result
import com.delicia.app.data.repo.ProductRepository
import com.delicia.app.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCatalogUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Flow<Result<List<Product>>> = flow {
        try {
            emit(Result.Loading)
            val products = repository.getProducts()
            emit(Result.Success(products))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error desconocido"))
        }
    }
}