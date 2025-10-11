package com.delicia.app.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.delicia.app.cart.CartManager
import com.delicia.app.databinding.ActivityProductDetailBinding
import com.delicia.app.domain.model.Product
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar para que tenga el botón de regreso
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtener el ID del producto que enviamos desde el adapter
        val productId = intent.getIntExtra("PRODUCT_ID", -1)

        if (productId == -1) {
            Toast.makeText(this, "Error al cargar el producto", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupObservers()
        viewModel.loadProduct(productId)
    }

    private fun setupObservers() {
        viewModel.product.observe(this) { product ->
            product?.let {
                bindProductData(it)
            } ?: run {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun bindProductData(product: Product) {
        binding.collapsingToolbar.title = product.name
        binding.productDescriptionTextView.text = product.description

        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
        binding.productPriceTextView.text = format.format(product.price)

        // Simulación de URLs de imagen
        val imageUrl = when (product.id) {
            1 -> "https://images.pexels.com/photos/267332/pexels-photo-267332.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            2 -> "https://images.pexels.com/photos/1387070/pexels-photo-1387070.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            3 -> "https://images.pexels.com/photos/175606/pexels-photo-175606.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            else -> "https://images.pexels.com/photos/896357/pexels-photo-896357.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        }
        binding.productImageView.load(imageUrl)

        binding.addToCartFab.setOnClickListener {
            CartManager.addProduct(product)
            Snackbar.make(binding.root, "${product.name} añadido al carrito", Snackbar.LENGTH_SHORT).show()
        }
    }

    // Para que el botón de regreso funcione
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}