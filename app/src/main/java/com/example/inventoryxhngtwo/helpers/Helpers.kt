package com.example.inventoryxhngtwo.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import java.io.ByteArrayOutputStream

fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-rotationDegrees.toFloat())
        postScale(-1f, -1f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}


fun Bitmap.toCompressedBitmap(): Bitmap {
    val maxHeight = 1024.0f
    val maxWidth = 1024.0f
    val scale = Math.min(maxHeight / height, maxWidth / width)

    val matrix = Matrix().apply {
        postScale(scale, scale)
    }
    val resizedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)

    val stream = ByteArrayOutputStream()
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
    val byteArray = stream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}


fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }else {
            android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}