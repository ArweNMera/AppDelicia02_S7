package com.delicia.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.delicia.app.data.local.relations.OrderWithItems
import com.delicia.app.databinding.ItemOrderBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryAdapter : ListAdapter<OrderWithItems, OrderHistoryAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderWithItems: OrderWithItems) {
            binding.orderIdTextView.text = "Pedido #${orderWithItems.order.orderId}"

            val sdf = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "ES"))
            binding.orderDateTextView.text = sdf.format(orderWithItems.order.orderDate)

            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
            binding.orderTotalTextView.text = format.format(orderWithItems.order.totalPrice)
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<OrderWithItems>() {
        override fun areItemsTheSame(oldItem: OrderWithItems, newItem: OrderWithItems): Boolean {
            return oldItem.order.orderId == newItem.order.orderId
        }

        override fun areContentsTheSame(oldItem: OrderWithItems, newItem: OrderWithItems): Boolean {
            return oldItem == newItem
        }
    }
}