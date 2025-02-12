package com.jihan.computils.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.CxSwingAnimation

@Composable
fun SwingAnimationDemo() {
    CxSwingAnimation(
        modifier = Modifier
            .background(color = Color(0XFF1c8bd2))
            .size(width = 220.dp, height = 195.dp),
        boardImage =  Icons.Default.AccountBox,
        textContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 10.dp, top = 90.dp, end = 10.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Opening",
                    color = Color(0XFFFF3749),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )
                Text(
                    text = "SOON!",
                    color = Color(0XFF111111),
                    fontSize = 29.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                )
            }
        },
        yOffset = (-28).dp,
        angleOffset = 3f
    )
}