package com.delicia.app.ui.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.core.Result
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.data.remote.ApiClient
import com.delicia.app.data.repo.ProductRepository
import com.delicia.app.domain.model.Product
import com.delicia.app.domain.usecase.GetCatalogUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val getCatalogUseCase: GetCatalogUseCase

    private val _catalogState = MutableLiveData<Result<List<Product>>>()
    val catalogState: LiveData<Result<List<Product>>> = _catalogState

    init {
        // Inicialización del repositorio y caso de uso
        val database = AppDatabase.getDatabase(application)
        val repository = ProductRepository(database.productDao(), ApiClient())
        getCatalogUseCase = GetCatalogUseCase(repository)
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            getCatalogUseCase().collect { result ->
                _catalogState.value = result
            }
        }
    }
}