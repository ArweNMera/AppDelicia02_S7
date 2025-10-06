package com.delicia.app.data.remote

import com.delicia.app.domain.model.Product
import kotlinx.coroutines.delay

class ApiClient : ProductService {
    override suspend fun getProducts(): List<Product> {
        // Simular latencia de red
        delay(1500)

        // Devolver datos harcodeados
        return listOf(
            Product(1, "Croissant de Mantequilla", "Clásico croissant francés, hojaldrado y delicioso.", 2.50, "url_croissant"),
            Product(2, "Baguette Tradicional", "Pan crujiente por fuera y tierno por dentro.", 1.80, "url_baguette"),
            Product(3, "Pan de Yema", "Pan dulce y suave, ideal para el desayuno.", 3.00, "url_pan_yema"),
            Product(4, "Muffin de Chocolate", "Muffin con trozos de chocolate amargo.", 2.75, "url_muffin"),
            Product(5, "Tarta de Manzana", "Porción individual de tarta casera de manzana.", 4.50, "url_tarta")
        )
    }
}