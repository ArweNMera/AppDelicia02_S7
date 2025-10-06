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
import com.delicia.app.domain.model.User // <-- Asegúrate que el import de User sea correcto
import com.delicia.app.ui.catalog.CatalogActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
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

                    // --- INICIO DE LA CORRECCIÓN ---
                    // Comprobamos que los datos son del tipo que esperamos (User)
                    if (result.data is User) {
                        // Dentro de este 'if', el compilador ya sabe que result.data es un User
                        Toast.makeText(this, "¡Bienvenido ${result.data.name}!", Toast.LENGTH_SHORT).show()

                        // Guardamos el estado de la sesión
                        SessionManager.saveLoginState(this, true)

                        startActivity(Intent(this, CatalogActivity::class.java))
                        finish()
                    }
                    // --- FIN DE LA CORRECCIÓN ---
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