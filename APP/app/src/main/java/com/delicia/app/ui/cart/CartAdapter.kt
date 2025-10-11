package com.delicia.app.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.delicia.app.databinding.ItemCartBinding
import com.delicia.app.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

// Creamos un tipo de dato para representar un item en el carrito
data class CartItem(val product: Product, val quantity: Int)

class CartAdapter(
    private val onIncrease: (Product) -> Unit,
    private val onDecrease: (Product) -> Unit,
    private val onDelete: (Product) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onIncrease, onDecrease, onDelete)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onIncrease: (Product) -> Unit,
        private val onDecrease: (Product) -> Unit,
        private val onDelete: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))

            binding.productNameTextView.text = cartItem.product.name
            binding.productPriceTextView.text = "${format.format(cartItem.product.price)} c/u"
            binding.quantityTextView.text = cartItem.quantity.toString()

            binding.increaseButton.setOnClickListener { onIncrease(cartItem.product) }
            binding.decreaseButton.setOnClickListener { onDecrease(cartItem.product) }
            binding.deleteButton.setOnClickListener { onDelete(cartItem.product) }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.product.id == newItem.product.id
        }
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}