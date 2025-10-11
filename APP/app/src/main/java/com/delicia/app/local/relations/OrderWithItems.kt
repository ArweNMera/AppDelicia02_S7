package com.delicia.app.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.delicia.app.data.local.entities.OrderEntity
import com.delicia.app.data.local.entities.OrderItemEntity

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>
)