package com.delicia.app.domain.usecase

import com.delicia.app.cart.CartManager
import com.delicia.app.domain.model.Product

class AddToCartUseCase {
    operator fun invoke(product: Product) {
        CartManager.addProduct(product)
    }
}