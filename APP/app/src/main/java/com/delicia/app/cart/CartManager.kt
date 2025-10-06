package com.delicia.app.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delicia.app.domain.model.Product

object CartManager {

    private val cartItems = mutableMapOf<Product, Int>()
    private val _cartLiveData = MutableLiveData<Map<Product, Int>>()
    val cartLiveData: LiveData<Map<Product, Int>> = _cartLiveData

    fun addProduct(product: Product) {
        val quantity = cartItems[product] ?: 0
        cartItems[product] = quantity + 1
        _cartLiveData.value = cartItems
    }

    fun getCartItems(): Map<Product, Int> {
        return cartItems.toMap()
    }

    fun getTotal(): Double {
        return cartItems.entries.sumOf { (product, quantity) ->
            product.price * quantity
        }
    }

    fun clearCart() {
        cartItems.clear()
        _cartLiveData.value = cartItems
    }
}