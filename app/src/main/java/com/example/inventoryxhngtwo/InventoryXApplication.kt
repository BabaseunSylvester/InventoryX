package com.example.inventoryxhngtwo

import android.app.Application
import com.example.inventoryxhngtwo.data.FuncProductsContainer
import com.example.inventoryxhngtwo.data.ProductsContainer

class InventoryXApplication : Application() {
    lateinit var container: ProductsContainer

    override fun onCreate() {
        super.onCreate()
        container = FuncProductsContainer(this)
    }
}