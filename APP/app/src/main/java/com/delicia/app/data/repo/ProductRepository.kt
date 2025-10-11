package com.delicia.app.data.repo

import com.delicia.app.data.local.ProductDao
import com.delicia.app.data.local.entities.ProductEntity
import com.delicia.app.data.remote.ApiClient
import com.delicia.app.domain.model.Product

// Creamos un repositorio para manejar la lógica de los datos de productos.
class ProductRepository(
    private val productDao: ProductDao,
    private val apiClient: ApiClient
) {

    // Esta función obtiene TODOS los productos, poblando la BBDD si es necesario.
    suspend fun getAllProducts(): List<Product> {
        val localProducts = productDao.getAllProducts()
        if (localProducts.isNotEmpty()) {
            return localProducts.map { it.toDomain() }
        } else {
            val remoteProducts = apiClient.getProducts()
            // Guardamos los productos de la API en la base de datos local
            productDao.insertAll(remoteProducts.map { it.toEntity() })
            return remoteProducts
        }
    }

    // Esta función busca productos en la base de datos local.
    suspend fun searchProducts(query: String): List<Product> {
        val searchQuery = "%$query%"
        return productDao.searchProductsByName(searchQuery).map { it.toDomain() }
    }
}

// Función de extensión para mapear de Modelo de Dominio a Entidad de BBDD
private fun Product.toEntity() = ProductEntity(id, name, description, price, imageUrl)