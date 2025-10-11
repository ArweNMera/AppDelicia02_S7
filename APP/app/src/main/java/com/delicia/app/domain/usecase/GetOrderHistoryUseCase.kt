package com.delicia.app.domain.usecase

import android.content.Context
import com.delicia.app.data.local.OrderDao
import com.delicia.app.data.local.SessionManager
import com.delicia.app.data.local.UserDao
import com.delicia.app.data.local.relations.OrderWithItems

class GetOrderHistoryUseCase(
    private val context: Context,
    private val orderDao: OrderDao,
    private val userDao: UserDao
) {
    suspend operator fun invoke(): Result<List<OrderWithItems>> {
        return try {
            val userEmail = SessionManager.getUserEmail(context)
                ?: return Result.failure(Exception("No se encontró sesión de usuario."))

            val user = userDao.getUserByEmail(userEmail)
                ?: return Result.failure(Exception("Usuario inválido."))

            val orders = orderDao.getOrdersForUser(user.id)
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}