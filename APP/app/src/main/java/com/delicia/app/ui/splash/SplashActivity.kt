package com.delicia.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.delicia.app.data.local.SessionManager
import com.delicia.app.databinding.ActivitySplashBinding
import com.delicia.app.ui.auth.LoginActivity
import com.delicia.app.ui.catalog.CatalogActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            // Comprueba si el usuario ya inició sesión
            if (SessionManager.isLoggedIn(this)) {
                // Si ya inició sesión, va directo al catálogo
                startActivity(Intent(this, CatalogActivity::class.java))
            } else {
                // Si no, va a la pantalla de login
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}