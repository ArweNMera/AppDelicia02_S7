package com.delicia.app.domain.usecase

import com.delicia.app.data.local.UserDao // Necesitarás un UserRepository, pero por simplicidad usamos el DAO
import com.delicia.app.data.local.entities.UserEntity

class RegisterUserUseCase(private val userDao: UserDao) {
    suspend operator fun invoke(email: String, pass: String): Result<Unit> {
        return try {
            if (userDao.getUserByEmail(email) != null) {
                Result.failure(Exception("El correo electrónico ya está registrado."))
            } else {
                val newUser = UserEntity(email = email, pass = pass)
                userDao.insertUser(newUser)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}