package com.delicia.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.delicia.app.data.local.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    // --- INICIO DE LA CORRECCIÓN ---
    // Quitamos los comodines '%' de la consulta. Ahora es más simple.
    @Query("SELECT * FROM products WHERE name LIKE :query")
    suspend fun searchProductsByName(query: String): List<ProductEntity>
    // --- FIN DE LA CORRECCIÓN ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?
}