package com.delicia.app.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.cart.CartManager
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.domain.model.Product
import com.delicia.app.domain.usecase.PlaceOrderUseCase
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val placeOrderUseCase: PlaceOrderUseCase

    private val _orderState = MutableLiveData<Result<Unit>>()
    val orderState: LiveData<Result<Unit>> = _orderState
    val cartItems = CartManager.cartLiveData

    init {
        val db = AppDatabase.getDatabase(application)
        placeOrderUseCase = PlaceOrderUseCase(application, db.orderDao(), db.userDao())
    }

    fun getTotal(): Double {
        return CartManager.getTotal()
    }

    fun placeOrder() {
        viewModelScope.launch {
            val result = placeOrderUseCase()
            _orderState.postValue(result)
        }
    }

    // --- AÑADE ESTAS FUNCIONES ---
    fun increaseQuantity(product: Product) {
        CartManager.addProduct(product)
    }

    fun decreaseQuantity(product: Product) {
        CartManager.removeProduct(product)
    }

    fun deleteProductFromCart(product: Product) {
        CartManager.deleteProduct(product)
    }
    // ---------------------------------
}