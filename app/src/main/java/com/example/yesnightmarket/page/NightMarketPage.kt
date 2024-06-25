package com.example.yesnightmarket.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.backHandler.HandleBackPress
import com.example.yesnightmarket.tool.savedataclass.readDataClass

@Composable
fun NightMarketPage(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val nightMarketname=readDataClass(context,"NightMarketName","")
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(top = statusBarHeight)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = nightMarketname, fontSize = 30.sp)
            }
        }
    ) {
        LazyColumn(modifier.padding(it)) {

        }
    }
    HandleBackPress {
        navController.navigate(Screens.MainPage.name)
    }
}