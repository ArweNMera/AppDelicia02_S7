package com.delicia.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.delicia.app.data.local.entities.OrderEntity
import com.delicia.app.data.local.entities.OrderItemEntity
import com.delicia.app.data.local.relations.OrderWithItems

@Dao
interface OrderDao {

    // Usamos @Transaction para asegurar que ambas operaciones (insertar la orden
    // y sus items) se completen exitosamente juntas. Si una falla, ninguna se realiza.
    @Transaction
    suspend fun insertOrderWithItems(order: OrderEntity, items: List<OrderItemEntity>) {
        val orderId = insertOrder(order)
        items.forEach { it.orderId = orderId }
        insertAllOrderItems(items)
    }

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long // Devuelve el ID de la nueva orden

    @Insert
    suspend fun insertAllOrderItems(items: List<OrderItemEntity>)

    @Transaction
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY orderDate DESC")
    suspend fun getOrdersForUser(userId: Int): List<OrderWithItems>
}