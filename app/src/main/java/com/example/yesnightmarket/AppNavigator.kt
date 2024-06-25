package com.example.yesnightmarket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


import com.example.yesnightmarket.page.LoginPage
import com.example.yesnightmarket.page.MainPage
import com.example.yesnightmarket.page.MessagePage
import com.example.yesnightmarket.page.NightMarketPage
import com.example.yesnightmarket.page.SalePage
import com.example.yesnightmarket.page.SettingPage
import com.example.yesnightmarket.page.ShowImagePage
import com.example.yesnightmarket.tool.custmozed.UdmImage
import com.google.accompanist.insets.LocalWindowInsets


@Composable
fun AppNavigator(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController() //管理應用程式中的導航流程
) {
    //   val context = LocalContext.current //獲取現在的context
    val insets = LocalWindowInsets.current
    val statusBarHeight = with(LocalDensity.current) { insets.statusBars.top.toDp() }

    val backStackEntry by navController.currentBackStackEntryAsState() //導航堆疊入口
    // 初始畫面位置
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route ?: Screens.MainPage.name//當前路徑，route回傳名稱

    )

    Surface(modifier = Modifier.fillMaxSize())
    {
        //NavHost導航容器，負責顯示不同目的地之間的轉換
        NavHost(
            navController, startDestination = currentScreen.name,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            //切換至MainPage
//            composable(Screens.EditPhotoInformationPage.name) {
//                EditPhotoInformationPage(modifier, navController)
//            }
            composable(Screens.LoginPage.name) {
                LoginPage(modifier, navController)
            }
            composable(Screens.MainPage.name) {
                MainPage(modifier, navController)
            }
            //切換至SecondPage
            composable(Screens.Message.name) {
                MessagePage(modifier, navController)
            }
            //切換至ThirdPage
            composable(Screens.SettingPage.name) {
                SettingPage(modifier, navController)
            }
            composable(Screens.Sale.name) {
                SalePage(modifier, navController)
            }
//            composable(Screens.EditPhotoPage.name) {
//                EditPhotoPage(modifier, navController)
//                //CoilImage()
//            }
            composable(Screens.ShowImagePage.name) {
                ShowImagePage(modifier, navController)
            }
            composable(Screens.NightMarket.name) {
                NightMarketPage(modifier, navController)
            }
//            composable(Screens.SearchPage.name) {
//                SearchPage(modifier, navController)
//            }
//        }
            // }
        }
    }
}



