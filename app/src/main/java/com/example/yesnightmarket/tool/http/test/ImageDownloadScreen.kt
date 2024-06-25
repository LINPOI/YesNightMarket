package com.example.yesnightmarket.tool.http.test
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream
@Composable
fun ImageDownloadScreen(imageUrl: String) {
    var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    val client = remember { OkHttpClient() }

    LaunchedEffect(imageUrl) {
        bitmap = downloadImage(client, imageUrl)
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    } else {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

suspend fun downloadImage(client: OkHttpClient, url: String): android.graphics.Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val inputStream: InputStream = response.body?.byteStream() ?: return@withContext null
                return@withContext BitmapFactory.decodeStream(inputStream)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}