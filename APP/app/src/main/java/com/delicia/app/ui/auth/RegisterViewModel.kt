package com.delicia.app.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val registerUserUseCase: RegisterUserUseCase

    // LiveData para comunicar el estado a la Activity
    private val _registrationState = MutableLiveData<Result<Unit>>()
    val registrationState: LiveData<Result<Unit>> = _registrationState

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        registerUserUseCase = RegisterUserUseCase(userDao)
    }

    fun registerUser(email: String, pass: String) {
        // Validación básica
        if (email.isBlank() || pass.isBlank()) {
            _registrationState.value = Result.failure(Exception("Correo y contraseña no pueden estar vacíos."))
            return
        }

        viewModelScope.launch {
            // Usamos un bloque try-catch por si el caso de uso lanza una excepción
            try {
                _registrationState.value = registerUserUseCase(email, pass)
            } catch (e: Exception) {
                _registrationState.value = Result.failure(e)
            }
        }
    }
}