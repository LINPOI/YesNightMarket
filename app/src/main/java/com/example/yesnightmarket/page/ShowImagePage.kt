package com.example.yesnightmarket.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.backHandler.BackHandlerPress
import com.example.yesnightmarket.tool.savedataclass.readDataClass

@Composable
fun ShowImagePage( modifier: Modifier = Modifier, navController: NavHostController){
    val context = LocalContext.current
        val scale by remember { mutableStateOf(1f) }
        val painter= readDataClass(context,"ShowImage","" )
        // Update the scale based on your requirements, e.g., user interaction or other state changes
        // For this example, we'll just demonstrate a fixed scale

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(painter),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale), // Apply the scale modifier
                contentScale = ContentScale.Crop
            )
        }
    BackHandlerPress( ){
        Log.i("9453","返回")
        navController.navigate(Screens.MainPage.name)
    }
}