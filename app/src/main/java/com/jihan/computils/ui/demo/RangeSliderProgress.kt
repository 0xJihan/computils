package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.CxRangeSliderWithPin

@Composable
fun RangeSliderWithPinDemo() {
    CxRangeSliderWithPin(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .fillMaxWidth(),
        trackInActiveColor = Color(0xFFFACBD9),
        trackColor = Color(0xffEF286D),
        startPinCircleColor = Color(0xFFEF286D),
        endPinCircleColor = Color(0xFFEF286D),
        startPinColor = Color(0xFFEF286D),
        endPinColor = Color(0xFFEF286D),
        startPinTextStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        ),
        endPinTextStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        ),
        trackThickness = 4.dp,
        pinCircleSize = 10.dp,
        startProgress = 10f,
        endProgress = 50f,
        pinSpacing = 16.dp,
        pinWidth = 40.dp,
        pinHeight = 40.dp
    ) { startProgressValue, endProgressValue ->

    }
}