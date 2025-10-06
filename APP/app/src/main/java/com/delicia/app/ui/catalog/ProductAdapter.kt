package com.delicia.app.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.delicia.app.databinding.ItemProductBinding
import com.delicia.app.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val onAddToCartClicked: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onAddToCartClicked)
    }

    class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, onAddToCartClicked: (Product) -> Unit) {
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
            binding.productPrice.text = format.format(product.price)

            binding.addToCartButton.setOnClickListener {
                onAddToCartClicked(product)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}