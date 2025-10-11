package com.delicia.app.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.delicia.app.databinding.ActivityCartBinding
import com.delicia.app.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Mi Carrito"

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { product -> viewModel.increaseQuantity(product) },
            onDecrease = { product -> viewModel.decreaseQuantity(product) },
            onDelete = { product -> viewModel.deleteProductFromCart(product) }
        )
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupObservers() {
        viewModel.cartItems.observe(this) { cartItemsMap ->
            if (cartItemsMap.isNullOrEmpty()) {
                binding.emptyCartTextView.visibility = View.VISIBLE
                binding.cartRecyclerView.visibility = View.GONE
                binding.checkoutButton.isEnabled = false
            } else {
                binding.emptyCartTextView.visibility = View.GONE
                binding.cartRecyclerView.visibility = View.VISIBLE
                binding.checkoutButton.isEnabled = true
                // Convertimos el Mapa a una Lista de CartItem para el adapter
                val cartItemList = cartItemsMap.map { CartItem(it.key, it.value) }
                cartAdapter.submitList(cartItemList)
            }
            // Actualizamos el total
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
            binding.totalAmountTextView.text = "Total: ${format.format(viewModel.getTotal())}"
        }

        viewModel.orderState.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "¡Pedido realizado con éxito!", Toast.LENGTH_LONG).show()
                finish()
            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.checkoutButton.setOnClickListener {
            viewModel.placeOrder()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}