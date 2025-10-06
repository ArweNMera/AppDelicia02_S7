package com.delicia.app.data.repo

import com.delicia.app.data.local.ProductDao
import com.delicia.app.data.remote.ApiClient
import com.delicia.app.domain.model.Product

class ProductRepository(private val productDao: ProductDao, private val apiClient: ApiClient) {

    suspend fun getProducts(): List<Product> {
        // 1. Intentar obtener de la base de datos local
        val localProducts = productDao.getAllProducts()

        if (localProducts.isNotEmpty()) {
            // Mapear de Entity a Model
            return localProducts.map { Product(it.id, it.name, it.description, it.price, it.imageUrl) }
        }

        // 2. Si la BD está vacía, obtener de la API simulada
        val remoteProducts = apiClient.getProducts()

        // 3. Guardar en la base de datos local para la próxima vez
        productDao.insertAll(remoteProducts.map { it.toEntity() })

        return remoteProducts
    }
}

// Función de extensión para mapear
fun Product.toEntity() = com.delicia.app.data.local.entities.ProductEntity(id, name, description, price, imageUrl)