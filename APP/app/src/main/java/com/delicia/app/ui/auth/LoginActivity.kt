package com.delicia.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.delicia.app.core.Result
import com.delicia.app.data.local.SessionManager
import com.delicia.app.databinding.ActivityLoginBinding
import com.delicia.app.domain.model.User
import com.delicia.app.ui.catalog.CatalogActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.loginButton.isEnabled = false
                }

                is Result.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true

                    if (result.data is User) {
                        val user = result.data as User
                        Toast.makeText(this, "¡Bienvenido ${user.name}!", Toast.LENGTH_SHORT).show()

                        // 🔹 Guardamos el estado y el email del usuario en la sesión
                        SessionManager.saveLoginState(this, true, user.email)

                        startActivity(Intent(this, CatalogActivity::class.java))
                        finish()
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
