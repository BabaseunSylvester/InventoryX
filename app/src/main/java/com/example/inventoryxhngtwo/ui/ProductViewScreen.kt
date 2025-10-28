package com.example.inventoryxhngtwo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.inventoryxhngtwo.R
import com.example.inventoryxhngtwo.data.Product
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun ProductViewScreen(
    product: Product?,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var listMenuState by rememberSaveable { mutableStateOf(false) }

    if (product != null) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
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
                        Box(modifier = Modifier.fillMaxSize()) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (product.image == null) {
                                    Image(
                                        painter = painterResource(R.drawable._52459_insert_photo_icon),
                                        contentDescription = null,
                                        alpha = 0.5f,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Image(
                                        bitmap = product.image.asImageBitmap(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(vertical = 32.dp, horizontal = 12.dp)
                                        .fillMaxWidth()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable._781859_dot_list_menu_nav_navigation_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clickable(role = Role.Button) {
                                                listMenuState = !listMenuState
                                            }
                                    )
                                }

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    DropdownMenu(
                                        expanded = listMenuState, onDismissRequest = { listMenuState = false },
                                        offset = DpOffset(x = 500000.dp, y = 70.dp)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Edit Product Details") },
                                            onClick = {
                                                listMenuState = false
                                                onEditClick(product.id)
                                            },
                                            leadingIcon = { Icon(painterResource(R.drawable._213412_compose_document_edit_pen_pencil_icon), null, Modifier.size(24.dp)) }
                                        )

                                        HorizontalDivider()

                                        DropdownMenuItem(
                                            text = { Text("Delete Product") },
                                            onClick = {
                                                listMenuState = false
                                                onDeleteClick(product.id)
                                            },
                                            leadingIcon = { Icon(painterResource(R.drawable.icons8_delete_trash_50), null, Modifier.size(24.dp)) }
                                        )
                                    }
                                }
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
                        value = product.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Product Name") },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = product.quantity,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Quantity in stock") },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    val currencySymbol = NumberFormat.getCurrencyInstance(Locale.of("en", "NG")).currency?.symbol ?: "$"

                    OutlinedTextField(
                        value = product.price,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Product Price") },
                        suffix = { Text(currencySymbol) },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                }

            }


        }
    }
}



@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Preview (showBackground = true, showSystemUi = true)
@Composable
fun ProductViewPreview() {
    ProductViewScreen(
        product = Product(1, "Test Product", "12", "$50.00", null),
        onEditClick = {},
        onDeleteClick = {}
    )
}