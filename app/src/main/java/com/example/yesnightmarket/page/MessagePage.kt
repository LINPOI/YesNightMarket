package com.example.yesnightmarket.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.backHandler.BackHandlerPress

@Composable
fun MessagePage(modifier: Modifier, navController: NavHostController) {
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(top = statusBarHeight)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text ="論壇", fontSize = 30.sp)
            }
        }
    ) {
        LazyColumn(modifier.padding(it)) {

        }
    }
    BackHandlerPress( ){
        Log.i("9453","返回")
        navController.navigate(Screens.MainPage.name)
    }
}