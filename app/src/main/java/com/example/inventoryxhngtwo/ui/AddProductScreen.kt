package com.example.inventoryxhngtwo.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.example.inventoryxhngtwo.R
import com.example.inventoryxhngtwo.helpers.uriToBitmap
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun AddProductScreen(
    productName: String,
    productQuantity: String,
    productPrice: String,
    productImage: Bitmap?,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onPhotoPicked: (Bitmap?) -> Unit,
    onCameraClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var imageCardState by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let {
            val pickedBitmap = uriToBitmap(context, it)
            onPhotoPicked(pickedBitmap)
            imageCardState = false
        }
    }


    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = if (imageCardState) {
                modifier
                    .fillMaxSize()
                    .blur(20.dp)
            }else {
                modifier
                    .fillMaxSize()
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1.5f)
            ) {
                Card(
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
                    elevation = CardDefaults.cardElevation(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = if (imageCardState){
                            Modifier
                                .fillMaxSize()
                        }else {
                            Modifier
                                .fillMaxSize()
                                .clickable(
                                    role = Role.Button,
                                ) { imageCardState = true }}
                    ) {
                        if (productImage == null) {
                            Icon(
                                painter = painterResource(R.drawable._11877_plus_round_icon),
                                contentDescription = null
                            )

                            Text(
                                text = "Add Product Image"
                            )
                        } else {
                            Image(
                                bitmap = productImage.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }


            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { onNameChange(it) },
                    enabled = !imageCardState,
                    singleLine = true,
                    label = { Text("Product Name") },
                    placeholder = { Text("Product Name") },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = productQuantity,
                    onValueChange = { onQuantityChange(it) },
                    enabled = !imageCardState,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Quantity in stock") },
                    placeholder = { Text("Quantity in stock") },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                val currencySymbol = NumberFormat.getCurrencyInstance(Locale.of("en", "NG")).currency?.symbol ?: "$"

                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { onPriceChange(it) },
                    enabled = !imageCardState,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = { Text("Product Price") },
                    placeholder = { Text("Product Price") },
                    suffix = { Text(currencySymbol) },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                ElevatedButton(onDoneClick, enabled = !imageCardState, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Done"
                    )
                }
            }

        }


        if (imageCardState) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = Color.Transparent)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(24.dp),
                    modifier = Modifier.size(400.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        IconButton(
                            onClick = {
                                onCameraClick()
                                imageCardState = false
                            }, Modifier.size(60.dp)) { Icon(painterResource(R.drawable._16123_camera_icon), contentDescription = null) }
                        IconButton(
                            onClick = {
                                imagePickerLauncher.launch(PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }, Modifier.size(60.dp)) { Icon(painterResource(R.drawable._52375_folder_open_icon), contentDescription = null) }
                    }
                }
            }
        }


    }
}



@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Preview (showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    AddProductScreen(
        productName = "",
        productQuantity = "",
        productPrice = "",
        productImage = null,
        onNameChange = {},
        onQuantityChange = {},
        onPriceChange = {},
        onCameraClick = {},
        onPhotoPicked = {},
        onDoneClick = {}
    )
}
