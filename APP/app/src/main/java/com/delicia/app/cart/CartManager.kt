package com.delicia.app.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delicia.app.domain.model.Product

object CartManager {

    private val cartItems = mutableMapOf<Product, Int>()
    private val _cartLiveData = MutableLiveData<Map<Product, Int>>()
    val cartLiveData: LiveData<Map<Product, Int>> = _cartLiveData

    fun addProduct(product: Product) {
        val currentQuantity = cartItems[product] ?: 0
        cartItems[product] = currentQuantity + 1
        _cartLiveData.value = cartItems
    }

    // --- AÑADE ESTA FUNCIÓN ---
    /** Disminuye la cantidad de un producto. Si llega a 0, lo elimina. */
    fun removeProduct(product: Product) {
        val currentQuantity = cartItems[product] ?: 0
        if (currentQuantity > 1) {
            cartItems[product] = currentQuantity - 1
        } else {
            cartItems.remove(product)
        }
        _cartLiveData.value = cartItems
    }

    // --- AÑADE ESTA OTRA FUNCIÓN ---
    /** Elimina un producto del carrito por completo, sin importar la cantidad. */
    fun deleteProduct(product: Product) {
        cartItems.remove(product)
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