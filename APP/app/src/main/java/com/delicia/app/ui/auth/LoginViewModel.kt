package com.delicia.app.ui.auth

import android.app.Application // <-- Importante
import androidx.lifecycle.AndroidViewModel // <-- Cambiamos a AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.core.Result
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.domain.model.User
import com.delicia.app.domain.usecase.AuthLoginUseCase
import kotlinx.coroutines.launch

// 1. Cambiamos ViewModel por AndroidViewModel y pedimos 'application' en el constructor
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // 2. Declaramos la variable para el caso de uso, pero la inicializamos después
    private val authLoginUseCase: AuthLoginUseCase

    private val _loginState = MutableLiveData<Result<User>>()
    val loginState: LiveData<Result<User>> = _loginState

    init {
        // 3. En el bloque init, obtenemos la base de datos, luego el dao,
        // y finalmente creamos el caso de uso pasándole el dao que necesita.
        val userDao = AppDatabase.getDatabase(application).userDao()
        authLoginUseCase = AuthLoginUseCase(userDao)
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            val result = authLoginUseCase(email, pass)
            _loginState.value = result
        }
    }
}