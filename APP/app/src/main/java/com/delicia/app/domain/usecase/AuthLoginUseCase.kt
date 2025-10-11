package com.delicia.app.domain.usecase

import com.delicia.app.core.Result // Tu clase sellada
import com.delicia.app.data.local.UserDao
import com.delicia.app.domain.model.User

class AuthLoginUseCase(private val userDao: UserDao) {
    suspend operator fun invoke(email: String, pass: String): Result<User> {
        return try {
            val userEntity = userDao.getUserByEmail(email)
            if (userEntity != null && userEntity.pass == pass) {
                // Mapeamos de UserEntity (BBDD) a User (Modelo de Dominio)
                val loggedInUser = User(id = userEntity.id.toString(), name = "Usuario", email = userEntity.email)
                Result.Success(loggedInUser)
            } else {
                Result.Error("Credenciales incorrectas.")
            }
        } catch (e: Exception) {
            Result.Error("Error al iniciar sesión: ${e.message}")
        }
    }
}