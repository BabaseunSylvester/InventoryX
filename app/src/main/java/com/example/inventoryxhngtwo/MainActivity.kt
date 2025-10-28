package com.example.inventoryxhngtwo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventoryxhngtwo.ui.CameraScreen
import com.example.inventoryxhngtwo.ui.NavComposable
import com.example.inventoryxhngtwo.ui.theme.InventoryXHNGTWOTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryXHNGTWOTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavComposable(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

