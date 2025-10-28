package com.example.inventoryxhngtwo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inventoryxhngtwo.helpers.Converters

@Database (entities = [Product::class], version = 1)
@TypeConverters (Converters::class)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun productsDao() : ProductsDao

    companion object {
        @Volatile
        private var Instance: ProductsDatabase? = null

        fun getDatabase(context: Context): ProductsDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, ProductsDatabase::class.java, "products-database")
                    .fallbackToDestructiveMigration(dropAllTables = false)
                    .build()
                    .also { Instance = it }
            }
        }
    }

}