package com.delicia.app.domain.usecase

import com.delicia.app.core.Result
import com.delicia.app.domain.model.User
import kotlinx.coroutines.delay

class AuthLoginUseCase {
    suspend operator fun invoke(email: String, pass: String): Result<User> {
        delay(1000) // Simular llamada a servidor
        return if (email == "cliente@delicia.com" && pass == "12345") {
            Result.Success(User("uid123", "Juan Pérez", email))
        } else {
            Result.Error("Credenciales incorrectas.")
        }
    }
}