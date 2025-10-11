package com.delicia.app.ui.catalog

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.delicia.app.R
import com.delicia.app.databinding.ItemProductBinding
import com.delicia.app.domain.model.Product
import com.delicia.app.ui.detail.ProductDetailActivity
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val onAddToCartClicked: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onAddToCartClicked)
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onAddToCartClicked: (Product) -> Unit) {
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
            binding.productPrice.text = format.format(product.price)

            // Simulación de URLs de imagen
            val imageUrl = when (product.id) {
                1 -> "https://images.pexels.com/photos/267332/pexels-photo-267332.jpeg"
                2 -> "https://images.pexels.com/photos/1387070/pexels-photo-1387070.jpeg"
                3 -> "https://images.pexels.com/photos/175606/pexels-photo-175606.jpeg"
                4 -> "https://images.pexels.com/photos/896357/pexels-photo-896357.jpeg"
                else -> "https://images.pexels.com/photos/2067423/pexels-photo-2067423.jpeg"
            }

            binding.productImageView.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            // Botón agregar al carrito
            binding.addToCartButton.setOnClickListener {
                onAddToCartClicked(product)
            }

            // Click en la tarjeta → abrir ProductDetailActivity
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("PRODUCT_ID", product.id)
                }
                context.startActivity(intent)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }
}
