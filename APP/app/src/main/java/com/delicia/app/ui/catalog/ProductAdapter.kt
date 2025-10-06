package com.delicia.app.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.delicia.app.R
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

            // Simulación de URLs de imagen para la demo
            val imageUrl = when (product.id) {
                1 -> "https://images.pexels.com/photos/267332/pexels-photo-267332.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                2 -> "https://images.pexels.com/photos/1387070/pexels-photo-1387070.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                3 -> "https://images.pexels.com/photos/175606/pexels-photo-175606.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                4 -> "https://images.pexels.com/photos/896357/pexels-photo-896357.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                else -> "https://images.pexels.com/photos/2067423/pexels-photo-2067423.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            }

            binding.productImageView.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background) // Puedes crear un drawable placeholder mejor
                error(R.drawable.ic_launcher_background)
            }

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