package com.delicia.app.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.domain.model.Product
import com.delicia.app.domain.usecase.GetProductDetailsUseCase
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val getProductDetailsUseCase: GetProductDetailsUseCase

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    init {
        val productDao = AppDatabase.getDatabase(application).productDao()
        getProductDetailsUseCase = GetProductDetailsUseCase(productDao)
    }

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            val productDetails = getProductDetailsUseCase(productId)
            _product.postValue(productDetails)
        }
    }
}