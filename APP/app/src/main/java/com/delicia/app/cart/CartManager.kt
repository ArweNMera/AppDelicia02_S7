package com.delicia.app.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delicia.app.domain.model.Product

/**
 * Singleton en memoria para gestionar el carrito.
 * Al ser una operación en memoria (trabaja con un Map), es muy rápido y no bloquea la UI.
 * Esta es la implementación correcta para evitar ANRs (Application Not Responding).
 */
object CartManager {

    private val cartItems = mutableMapOf<Product, Int>()

    // Usamos LiveData para que en el futuro la UI pueda reaccionar a los cambios.
    private val _cartLiveData = MutableLiveData<Map<Product, Int>>()
    val cartLiveData: LiveData<Map<Product, Int>> = _cartLiveData

    /**
     * Añade un producto al carrito. Si ya existe, incrementa la cantidad.
     */
    fun addProduct(product: Product) {
        val currentQuantity = cartItems[product] ?: 0
        cartItems[product] = currentQuantity + 1
        _cartLiveData.value = cartItems // Notifica a los observadores (si los hay)
    }

    /**
     * Devuelve una copia del mapa de ítems del carrito.
     * Es una operación de lectura en memoria, por lo tanto, es instantánea.
     */
    fun getCartItems(): Map<Product, Int> {
        return cartItems.toMap()
    }

    /**
     * Calcula el precio total de todos los productos en el carrito.
     * Es un cálculo matemático simple, por lo tanto, es instantáneo.
     */
    fun getTotal(): Double {
        return cartItems.entries.sumOf { (product, quantity) ->
            product.price * quantity
        }
    }

    /**
     * Limpia todos los ítems del carrito.
     */
    fun clearCart() {
        cartItems.clear()
        _cartLiveData.value = cartItems
    }
}