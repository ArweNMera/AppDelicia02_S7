package com.delicia.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0L,
    val userId: Int, // Para saber qué usuario hizo el pedido
    val orderDate: Date,
    val totalPrice: Double
)