package com.example.yesnightmarket.tool.http.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yesnightmarket.Data.Item
import com.example.yesnightmarket.Data.Member
import com.example.yesnightmarket.tool.custmozed.log
import com.example.yesnightmarket.tool.custmozed.loge
import com.example.yesnightmarket.tool.http.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TestConnect() {
    Scaffold (Modifier.fillMaxSize()){


        Column(modifier = Modifier.padding(it).fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(onClick = {
                log("傳送")
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                       val member=Retrofit.apiService.addMember(Member("223@gmail.com","222222"))
                        log(member.toString())
                    } catch (e: Exception) {
                        // 处理错误
                        e.message?.let { it1 -> loge(it1) }
                    }
                }
            }) {
                Text(text = "傳送")
            }
            OutlinedButton(onClick = {
                log("查閱")
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val member = Retrofit.apiService.getMembers()
                        log(member.toString())
                    } catch (e: Exception) {
                        // 处理错误
                        e.message?.let { it1 -> loge(it1) }
                    }
                }
            }) {
                Text(text = "查閱")
            }
            OutlinedButton(onClick = {
                log("查閱個別")
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val member = Retrofit.apiService.getMemberByGmail("2222@gmail.com")
                        log(member.toString())
                    } catch (e: Exception) {
                        // 处理错误
                        e.message?.let { it1 -> loge(it1) }
                    }
                }
            }) {
                Text(text = "查閱個別")
            }
            OutlinedButton(onClick = {
                log("登入")
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val member = Retrofit.apiService.login(member = Member("2@gmail.com", "2222"))
                        if(member.isSuccessful){
                            log("登入成功")
                        }else if(member.code()==401){
                            log("密碼錯誤")
                        }else{
                            log("帳號錯誤")
                        }
                        log(member.toString())
                    } catch (e: Exception) {
                        // 处理错误
                        e.message?.let { it1 -> loge(it1) }
                    }
                }
            }) {
                Text(text = "登入")
            }

        }
    }
}
