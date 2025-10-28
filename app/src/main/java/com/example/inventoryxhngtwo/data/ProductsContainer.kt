package com.example.inventoryxhngtwo.data

import android.content.Context

interface ProductsContainer {
    val productsRepository: ProductsRepository
}


class FuncProductsContainer(context: Context) : ProductsContainer {
    override val productsRepository: ProductsRepository by lazy {
        FuncProductsRepository(ProductsDatabase.getDatabase(context).productsDao())
    }
}