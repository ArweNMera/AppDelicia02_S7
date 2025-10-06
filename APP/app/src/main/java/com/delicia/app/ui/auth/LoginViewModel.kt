package com.delicia.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delicia.app.core.Result
import com.delicia.app.domain.model.User
import com.delicia.app.domain.usecase.AuthLoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authLoginUseCase = AuthLoginUseCase()

    private val _loginState = MutableLiveData<Result<User>>()
    val loginState: LiveData<Result<User>> = _loginState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            val result = authLoginUseCase(email, pass)
            _loginState.value = result
        }
    }
}