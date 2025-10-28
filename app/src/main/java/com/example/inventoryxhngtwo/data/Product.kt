package com.example.inventoryxhngtwo.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "products")
data class Product(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String,
    val quantity: String,
    val price: String,
    val image: Bitmap? = null
)
