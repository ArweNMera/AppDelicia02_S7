package com.delicia.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val orderItemId: Long = 0L,

    // Esta es la "llave foránea" que conecta este item a una orden específica
    var orderId: Long = 0L,

    val productId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerItem: Double
)