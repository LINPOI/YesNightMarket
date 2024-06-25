package com.example.yesnightmarket.page

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.yesnightmarket.Data.NightMarket

import com.example.yesnightmarket.tool.custmozed.UdmImage
import com.example.yesnightmarket.R
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.NightMarketInfo
import com.example.yesnightmarket.tool.backHandler.DoubleBackHandler
import com.example.yesnightmarket.tool.custmozed.UdmtextFields
import com.example.yesnightmarket.tool.http.Retrofit
import com.example.yesnightmarket.tool.savedataclass.saveDataClass
import com.google.accompanist.insets.LocalWindowInsets

val statusBarHeight=30.dp
@Composable
fun MainPage(modifier: Modifier, navHostController: NavHostController) {
    val context = LocalContext.current
    var listNightMarket by remember {
        mutableStateOf(listOf<NightMarket>())
    }
    LaunchedEffect(Unit) {
        listNightMarket= Retrofit.apiService.getNightMarkets()
    }

    Scaffold(
        topBar = {
            Column(
                modifier
                    .border(0.5.dp, MaterialTheme.colorScheme.onBackground)
                    .fillMaxWidth()
                    .padding(top = statusBarHeight)
                ,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val location = UdmtextFields(
                        stringResource = R.string.nul,
                        modifier = Modifier,
                        selectFieldsStyle = 1,
                        keyboardType = KeyboardType.Text
                    ) {
                        //1.如同點選搜尋時執行指令

                    }
                    Icon(imageVector = Icons.TwoTone.Search, contentDescription = null,
                        modifier
                            .padding(start = 10.dp)
                            .size(40.dp)
                            .clickable {
                                //與1.相同

                            })
                }

            }
        },
        bottomBar = {
            BottomBar(modifier,navHostController)
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item() {

                listNightMarket.forEach {
                    NightMarketInfo(it,navHostController)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "常按複製，點兩下進入")
            }


        }

        DoubleBackHandler(context = context)
    }
}
@Composable
fun BottomBar(modifier: Modifier, navController: NavHostController){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, MaterialTheme.colorScheme.onBackground)
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UdmImage(imageResource = R.drawable.sale) {
            navController.navigate(Screens.Sale.name)
        }

        UdmImage(imageResource = R.drawable.message) {
            navController.navigate(Screens.Message.name)
        }
        UdmImage(imageResource = R.drawable.setting) {
            navController.navigate(Screens.SettingPage.name)
        }
    }
}