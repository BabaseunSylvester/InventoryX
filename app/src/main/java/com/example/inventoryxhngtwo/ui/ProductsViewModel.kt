package com.example.inventoryxhngtwo.ui

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventoryxhngtwo.InventoryXApplication
import com.example.inventoryxhngtwo.data.Product
import com.example.inventoryxhngtwo.data.ProductsRepository
import com.example.inventoryxhngtwo.helpers.toCompressedBitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _newProductUiState = MutableStateFlow(NewProductUiState())
    val newProductUiState = _newProductUiState.asStateFlow()


    fun onNameChange(value: String) {
        _newProductUiState.update {
            it.copy(name = value)
        }
    }

    fun onQuantityChange(value: String) {
        _newProductUiState.update {
            it.copy(quantity = value)
        }
    }

    fun onPriceChange(value: String) {
        _newProductUiState.update {
            it.copy(price = value)
        }
    }

    fun updateImage(image: Bitmap?) {
        _newProductUiState.update {
            it.copy(image = image?.toCompressedBitmap())
        }
    }


    fun addProduct(name: String, quantity: String, price: String, image: Bitmap?) {
        viewModelScope.launch {
            productsRepository.addProduct(name, quantity, price, image)
        }

        _newProductUiState.update { it.copy("", "", "", null) }
    }



    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            productsRepository.removeProduct(id)
        }

        _newProductUiState.update { it.copy("", "", "", null) }
    }




    val allProductsState: StateFlow<AllProductsState> = productsRepository.getAllProducts()
        .map {
            AllProductsState(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            AllProductsState()
        )




    private val _currentViewIdState = MutableStateFlow(CurrentViewIdState())

    var x: StateFlow<CurrentViewProductState> = MutableStateFlow(CurrentViewProductState()).asStateFlow()

    fun updateCurrentView(id: Int) {
        _currentViewIdState.update { it.copy(id) }

        x = productsRepository.getProduct(_currentViewIdState.value.productId)
            .map {
                CurrentViewProductState(it)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000L),
                CurrentViewProductState()
            )
    }







    private val _currentEditProductState = MutableStateFlow(CurrentEditProductState())
    val currentEditProductState = _currentEditProductState.asStateFlow()

    fun updateEditProduct(id: Int) {
        x.value.product?.let { product ->
            _currentEditProductState.update {
                it.copy(
                    id = id,
                    name = product.name,
                    quantity = product.quantity,
                    price = product.price,
                    image = product.image
                )
            }
        }
    }

    fun onEditNameChange(value: String) {
        _currentEditProductState.update { it.copy(name = value) }
    }

    fun onEditQuantityChange(value: String) {
        _currentEditProductState.update { it.copy(quantity = value) }
    }

    fun onEditPriceChange(value: String) {
        _currentEditProductState.update { it.copy(price = value) }
    }

    fun onEditImageChange(image: Bitmap?) {
        _currentEditProductState.update { it.copy(image = image?.toCompressedBitmap()) }
    }



    fun editProduct(id: Int) {
        viewModelScope.launch {
            productsRepository.updateProduct(
                id,
                currentEditProductState.value.name,
                _currentEditProductState.value.quantity,
                _currentEditProductState.value.price,
                _currentEditProductState.value.image
            )
        }
        _newProductUiState.update { it.copy("", "", "", null) }
        _currentEditProductState.update { it.copy(0,"", "", "", null) }
    }






    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as InventoryXApplication
                ProductsViewModel(application.container.productsRepository)
            }
        }
    }
}



data class NewProductUiState(
    val name: String = "",
    val quantity: String = "",
    val price: String = "",
    val image: Bitmap? = null
)

data class CurrentViewProductState(
    val product: Product? = null
)

data class CurrentViewIdState(
    var productId: Int = 0
)

data class AllProductsState(
    val products: List<Product> = emptyList()
)

data class CurrentEditProductState(
    val id: Int = 0,
    val name: String = "",
    val quantity: String = "",
    val price: String = "",
    val image: Bitmap? = null
)

