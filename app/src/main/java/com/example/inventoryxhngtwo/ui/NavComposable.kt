package com.example.inventoryxhngtwo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.Delegates


enum class Routes {
    HomeScreen, ProductScreen, AddProductScreen, EditProductScreen, CameraScreen
}




@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun NavComposable(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val productsViewModel: ProductsViewModel = viewModel(factory = ProductsViewModel.Factory)

    val newProductUiState = productsViewModel.newProductUiState.collectAsState().value
    val allProductState = productsViewModel.allProductsState.collectAsState().value

    val currentViewProductState = productsViewModel.x.collectAsState().value

    val currentEditProductState = productsViewModel.currentEditProductState.collectAsState().value

    var screenDeterminer by rememberSaveable { mutableIntStateOf(0) }



    NavHost(
        navController = navController,
        startDestination =  Routes.HomeScreen.name
    ) {
        composable(Routes.HomeScreen.name) {
            HomeScreen(
                productsList = allProductState.products,
                onAddClick = {
                    screenDeterminer = 0
                    navController.navigate(Routes.AddProductScreen.name)
                },
                onProductClick = {
                    productsViewModel.updateCurrentView(it)
                    navController.navigate(Routes.ProductScreen.name)
                }
            )
        }

        composable(Routes.AddProductScreen.name) {
            AddProductScreen(
                productName = newProductUiState.name,
                productQuantity = newProductUiState.quantity,
                productPrice = newProductUiState.price,
                productImage = newProductUiState.image,
                onNameChange = { productsViewModel.onNameChange(it) },
                onQuantityChange = { productsViewModel.onQuantityChange(it) },
                onPriceChange = { productsViewModel.onPriceChange(it) },
                onPhotoPicked = {
                    productsViewModel.updateImage(it)
                },
                onCameraClick = { navController.navigate(Routes.CameraScreen.name) },
                onDoneClick = {
                    productsViewModel.addProduct(
                        newProductUiState.name,
                        newProductUiState.quantity,
                        newProductUiState.price,
                        newProductUiState.image
                    )

                    navController.popBackStack()
                }
            )
        }

        composable(Routes.ProductScreen.name) {
            ProductViewScreen(
                product = currentViewProductState.product,
                onEditClick = {
                    productsViewModel.updateEditProduct(it)
                    screenDeterminer = 1
                    navController.navigate(Routes.EditProductScreen.name)
                },
                onDeleteClick = {
                    navController.popBackStack()
                    productsViewModel.deleteProduct(it)
                }
            )
        }

        composable(Routes.EditProductScreen.name) {
            AddProductScreen(
                productName = currentEditProductState.name,
                productQuantity = currentEditProductState.quantity,
                productPrice = currentEditProductState.price,
                productImage = currentEditProductState.image,
                onNameChange = { productsViewModel.onEditNameChange(it) },
                onQuantityChange = { productsViewModel.onEditQuantityChange(it) },
                onPriceChange = { productsViewModel.onEditPriceChange(it) },
                onPhotoPicked = { productsViewModel.onEditImageChange(it) },
                onCameraClick = { navController.navigate(Routes.CameraScreen.name) },
                onDoneClick = {
                    productsViewModel.editProduct(
                        currentEditProductState.id
                    )

                    navController.popBackStack(Routes.ProductScreen.name, false)
                }
            )
        }

        composable(Routes.CameraScreen.name) {
            CameraScreen(
                onPhotoCaptured = {
                    if (screenDeterminer == 0) {
                        productsViewModel.updateImage(it)
                        navController.popBackStack(route = Routes.AddProductScreen.name, inclusive = false)
                    } else {
                        productsViewModel.onEditImageChange(it)
                        navController.popBackStack(route = Routes.EditProductScreen.name, inclusive = false)
                    }

                }
            )
        }

    }



}