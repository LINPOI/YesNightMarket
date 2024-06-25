package com.example.yesnightmarket.tool.http.test

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.yesnightmarket.page.uploadImageWithDetails
import com.example.yesnightmarket.tool.custmozed.log
import com.example.yesnightmarket.tool.http.Retrofit
import com.example.yesnightmarket.tool.http.URL
import com.example.yesnightmarket.tool.pictureTool.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@Composable
fun ImageUploadWithDetailsScreen() {
    val coroutineScope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadResult by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var fileName by remember { mutableStateOf("") }  // 新增用于输入文件名的状态
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = fileName,
            onValueChange = { fileName = it },
            label = { Text("文件名") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("图片描述") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("用户ID") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("選擇圖片")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            selectedImageUri?.let { uri ->
                coroutineScope.launch {
                    uploadImageWithDetails(context, uri, description, userId, fileName)
                }
            }
        }) {
            Text("上传图片")
        }
        Image(painter = rememberAsyncImagePainter(model = "http://192.168.50.80:3000/uploads/了呢.jpg", onError = {
            Log.e("linpoi", "painter:"+it.result.throwable.message!!)
        }), contentDescription = null)
        uploadResult?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
        }
    }
}


