package com.jihan.computils.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.CxColorEffectText

@Composable
fun ColorEffectTextDemo() {
    CxColorEffectText(
        text = "Don’t allow people to dim your shine because they are blinded.",
        textStyle = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        colors = listOf(
            Color(0xFFD79B63),
            Color(0xFF58B9E6),
            Color(0xFF6D7AE0),
            Color(0xFFB962EE)
        )
    )
}