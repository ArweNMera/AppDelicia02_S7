package com.delicia.app.data.remote
import com.delicia.app.domain.model.Product

// Interfaz para mantener la estructura, aunque la implementación sea simulada
interface ProductService {
    suspend fun getProducts(): List<Product>
}