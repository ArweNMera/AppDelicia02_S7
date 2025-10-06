package com.delicia.app.ui.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delicia.app.cart.CartManager
import com.delicia.app.databinding.ActivityCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Mi Carrito"

        displayCartItems()
    }

    override fun onResume() {
        super.onResume()
        displayCartItems()
    }

    private fun displayCartItems() {
        val cartItems = CartManager.getCartItems()
        val cartText = StringBuilder()

        if (cartItems.isEmpty()) {
            cartText.append("El carrito está vacío.")
        } else {
            cartItems.forEach { (product, quantity) ->
                cartText.append("- ${product.name} (x$quantity)\n")
            }
        }

        binding.cartItemsTextView.text = cartText.toString()

        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
        binding.totalAmountTextView.text = "Total: ${format.format(CartManager.getTotal())}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}