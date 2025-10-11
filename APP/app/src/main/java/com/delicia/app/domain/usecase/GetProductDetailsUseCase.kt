package com.delicia.app.domain.usecase

import com.delicia.app.data.local.ProductDao
import com.delicia.app.domain.model.Product

class GetProductDetailsUseCase(private val productDao: ProductDao) {
    suspend operator fun invoke(productId: Int): Product? {
        val entity = productDao.getProductById(productId)
        // Mapeamos de Entidad de BBDD a Modelo de Dominio
        return entity?.let {
            Product(it.id, it.name, it.description, it.price, it.imageUrl)
        }
    }
}