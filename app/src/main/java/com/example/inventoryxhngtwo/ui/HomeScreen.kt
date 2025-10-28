package com.example.inventoryxhngtwo.ui

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import com.example.inventoryxhngtwo.R
import com.example.inventoryxhngtwo.data.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    productsList: List<Product>,
    onAddClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    if (!cameraPermissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inventory List", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = RoundedCornerShape(100)
            ) {
                Icon(painterResource(R.drawable._11877_plus_round_icon), null, modifier = Modifier.size(75.dp))
            }
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(productsList) {
                    ProductItem(it, { it -> onProductClick(it) }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                }
            }
        }
    }

}



@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onProductClick(product.id) },
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .height(250.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (product.image != null) {
                Image(
                    bitmap = product.image.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable._52459_insert_photo_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    alpha = 0.5f
                )
            }

            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )


            HorizontalDivider(modifier = Modifier.weight(0.1f))

            val priceValue = product.price.replace(Regex("[^\\d.]"), "").toDoubleOrNull() ?: 0.0
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.of("en", "NG")).format(priceValue)
            Text(
                text = formattedPrice,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
            )
        }
    }
}



@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Preview (showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        Product(1, "Test Product", "12", "$50.00", createBitmap(40, 40, Bitmap.Config.RGB_565)), {}
    )
}
