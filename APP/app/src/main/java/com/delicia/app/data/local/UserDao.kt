package com.delicia.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.delicia.app.data.local.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
}