package com.example.yesnightmarket.tool.custmozed

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager

@Composable
fun UdmText(string: String) {
    val clipboardManager = LocalClipboardManager.current
    Text(text = string,modifier =  Modifier.pointerInput(Unit){
        detectTapGestures(onLongPress = {
            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(string))
        })
    })
}