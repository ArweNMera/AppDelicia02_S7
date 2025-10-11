package com.delicia.app.data.repo

import com.delicia.app.data.local.entities.ProductEntity
import com.delicia.app.domain.model.Product

/**
 * Este archivo contendrá todas las funciones para "mapear" o convertir
 * objetos de la capa de datos (como Entities) a objetos de la capa de dominio (los modelos
 * que usa la UI).
 */

// Este es el "traductor" de ProductEntity (BBDD) a Product (Dominio/UI)
fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageUrl = this.imageUrl
    )
}