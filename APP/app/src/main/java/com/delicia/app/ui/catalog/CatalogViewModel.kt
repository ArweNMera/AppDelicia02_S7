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
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val getCatalogUseCase: GetCatalogUseCase

    private val _catalogState = MutableLiveData<Result<List<Product>>>()
    val catalogState: LiveData<Result<List<Product>>> = _catalogState

    init {
        // Construimos la cadena de dependencias completa y correcta
        val productDao = AppDatabase.getDatabase(application).productDao()
        val apiClient = ApiClient()
        val repository = ProductRepository(productDao, apiClient)
        getCatalogUseCase = GetCatalogUseCase(repository)

        // Carga inicial de todos los productos
        fetchProducts("")
    }

    fun fetchProducts(query: String) {
        viewModelScope.launch {
            getCatalogUseCase(query).collect { result ->
                _catalogState.value = result
            }
        }
    }
}