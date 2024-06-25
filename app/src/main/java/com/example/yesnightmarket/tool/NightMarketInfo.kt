package com.example.yesnightmarket.tool

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.yesnightmarket.Data.NightMarket
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.savedataclass.saveDataClass

@Composable
fun NightMarketInfo(nightMarket: NightMarket, navController: NavHostController) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Column(
        Modifier
            .border(0.5.dp, Color.Gray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        navController.navigate(Screens.NightMarket.name)
                        saveDataClass(context, "NightMarketName", nightMarket.name)
                    }
                )
            }) {
        Row(
            Modifier
                .fillMaxWidth()
                .border(0.5.dp, Color.Gray), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = nightMarket.name, fontSize = 30.sp)
        }
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = nightMarket.imageUrl, onError = {
                    Log.e("linpoi", "painter:" + it.result.throwable.message!!)
                }),
                modifier = Modifier
                    .size(150.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                navController.navigate(Screens.NightMarket.name)
                                saveDataClass(context, "NightMarketName", nightMarket.name)
                            },
                            onLongPress = {
                                saveDataClass(context, "ShowImage", nightMarket.imageUrl)
                                clipboardManager.setText(
                                    androidx.compose.ui.text.AnnotatedString(
                                        nightMarket.imageUrl
                                    )
                                )
                                navController.navigate(Screens.ShowImagePage.name)

                            })
                    },
                contentDescription = null
            )
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "地址：")
                Text(text = "　${nightMarket.address}", modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            navController.navigate(Screens.NightMarket.name)
                            saveDataClass(context, "NightMarketName", nightMarket.name)
                        },
                        onLongPress = {
                            clipboardManager.setText(
                                androidx.compose.ui.text.AnnotatedString(
                                    nightMarket.address
                                )
                            )
                        })
                })
                Text(text = "電話：${nightMarket.phone}", modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            navController.navigate(Screens.NightMarket.name)
                            saveDataClass(context, "NightMarketName", nightMarket.name)
                        }, onLongPress = {
                            clipboardManager.setText(
                                androidx.compose.ui.text.AnnotatedString(
                                    nightMarket.phone
                                )
                            )
                        })
                })
                Text(
                    text = "營業時間：${nightMarket.openingHours}",
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                            navController.navigate(Screens.NightMarket.name)
                            saveDataClass(context, "NightMarketName", nightMarket.name)
                        }, onLongPress = {
                            clipboardManager.setText(
                                androidx.compose.ui.text.AnnotatedString(
                                    nightMarket.address
                                )
                            )
                        })
                    })
            }
        }
    }

}