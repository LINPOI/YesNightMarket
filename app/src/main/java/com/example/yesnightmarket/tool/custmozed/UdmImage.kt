package com.example.yesnightmarket.tool.custmozed

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UdmImage(imageResource:Int,size:Dp=100.dp,width: Dp =size,height:Dp=size,modifier: Modifier = Modifier, press:()->Unit={}) {
    //71.9 60
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = modifier
            .padding(bottom = 0.5.dp)
            //.border(0.5.dp,Color.Black)
            .size(width = width, height =height)
//            .padding(top = 5.dp)
            .animateContentSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        press()
                    },
                    onLongPress = {
                        //長按設置
                    }
                )
            }
            //保護不被ui系統遮擋
            .safeDrawingPadding(),
    )
}