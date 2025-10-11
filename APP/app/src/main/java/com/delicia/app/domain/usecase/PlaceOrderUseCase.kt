package com.delicia.app.domain.usecase

import android.content.Context
import com.delicia.app.cart.CartManager
import com.delicia.app.data.local.OrderDao
import com.delicia.app.data.local.SessionManager
import com.delicia.app.data.local.UserDao
import com.delicia.app.data.local.entities.OrderEntity
import com.delicia.app.data.local.entities.OrderItemEntity
import java.util.Date

class PlaceOrderUseCase(
    private val context: Context,
    private val orderDao: OrderDao,
    private val userDao: UserDao
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val cartItems = CartManager.getCartItems()
            if (cartItems.isEmpty()) {
                return Result.failure(Exception("El carrito está vacío."))
            }

            // 1. Obtener el usuario actual a través del email guardado en la sesión
            val userEmail = SessionManager.getUserEmail(context)
                ?: return Result.failure(Exception("No se encontró sesión de usuario."))

            val user = userDao.getUserByEmail(userEmail)
                ?: return Result.failure(Exception("El usuario de la sesión es inválido."))

            // 2. Crear la entidad principal de la orden
            val order = OrderEntity(
                userId = user.id,
                orderDate = Date(),
                totalPrice = CartManager.getTotal()
            )

            // 3. Crear la lista de todos los items dentro de la orden
            val orderItems = cartItems.map { (product, quantity) ->
                OrderItemEntity(
                    // El orderId se asignará automáticamente en el DAO
                    productId = product.id,
                    productName = product.name,
                    quantity = quantity,
                    pricePerItem = product.price
                )
            }

            // 4. Insertar la orden y sus items en la base de datos
            orderDao.insertOrderWithItems(order, orderItems)

            // 5. Si todo fue exitoso, limpiar el carrito
            CartManager.clearCart()

            Result.success(Unit)
        } catch (e: Exception) {
            // Si algo falla, devolver el error
            Result.failure(e)
        }
    }
}