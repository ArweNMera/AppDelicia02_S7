package com.delicia.app.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delicia.app.cart.CartManager
import com.delicia.app.data.local.SessionManager
import com.delicia.app.databinding.ActivityProfileBinding
import com.delicia.app.ui.auth.LoginActivity
import com.delicia.app.ui.history.OrderHistoryActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Mi Perfil"

        // 1️⃣ Mostrar el email del usuario logueado
        val userEmail = SessionManager.getUserEmail(this)
        binding.userEmailTextView.text = userEmail ?: "No se encontró el email"

        // 2️⃣ Listener para ver historial de pedidos
        binding.myOrdersButton.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }

        // 3️⃣ Listener para cerrar sesión
        binding.logoutButton.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout() {
        // 🧹 Limpiar el carrito
        CartManager.clearCart()

        // 🧹 Limpiar sesión guardada (estado y email)
        SessionManager.logout(this)

        // 🚪 Redirigir al login y cerrar las pantallas previas
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
