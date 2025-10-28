package com.example.inventoryxhngtwo.data

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun addProduct(name: String, quantity: String, price: String, image: Bitmap?)

    fun getAllProducts() : Flow<List<Product>>

    fun getProduct(id: Int) : Flow<Product?>

    suspend fun removeProduct(id: Int)

    suspend fun updateProduct(id: Int, name: String, quantity: String, price: String, image: Bitmap?)

}


class FuncProductsRepository(private val productsDao: ProductsDao) : ProductsRepository {

    override suspend fun addProduct(name: String, quantity: String, price: String, image: Bitmap?) {
        productsDao.addProduct(name, quantity, price, image)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productsDao.getAllProducts()
    }

    override fun getProduct(id: Int): Flow<Product?> {
        return productsDao.getProduct(id)
    }

    override suspend fun removeProduct(id: Int) {
        productsDao.removeProduct(id)
    }

    override suspend fun updateProduct(
        id: Int,
        name: String,
        quantity: String,
        price: String,
        image: Bitmap?
    ) {
        productsDao.updateProduct(id, name, quantity, price, image)
    }

}