package com.example.inventoryxhngtwo.data

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query ("INSERT INTO products(name, quantity, price, image) VALUES (:name, :quantity, :price, :image)")
    suspend fun addProduct(name: String, quantity: String, price: String, image: Bitmap?)

    @Query ("SELECT * FROM products")
    fun getAllProducts() : Flow<List<Product>>

    @Query ("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: Int) : Flow<Product?>

    @Query ("DELETE FROM products WHERE id = :id")
    suspend fun removeProduct(id: Int)

    @Query ("UPDATE products SET name = :name, quantity = :quantity, price = :price, image = :image WHERE id = :id")
    suspend fun updateProduct(id: Int, name: String, quantity: String, price: String, image: Bitmap?)

}