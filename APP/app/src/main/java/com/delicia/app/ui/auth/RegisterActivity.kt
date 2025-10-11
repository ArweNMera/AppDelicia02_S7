package com.delicia.app.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.delicia.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            viewModel.registerUser(email, pass)
        }

        binding.loginLink.setOnClickListener {
            // Cierra esta actividad y regresa a la de Login
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.registrationState.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "¡Cuenta creada con éxito! Por favor, inicia sesión.", Toast.LENGTH_LONG).show()
                finish() // Cierra la pantalla de registro
            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}