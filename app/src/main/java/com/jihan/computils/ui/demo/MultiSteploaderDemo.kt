package com.jihan.computils.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.MultiStepLoader
import com.jihan.composeutils.ProgressItem

@Composable
fun MultiStepLoaderDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MultiStepLoader(
            modifier = Modifier
                .fillMaxSize(),
            size = 100.dp,
            strokeWidth = 16.dp,
            spacing = 5.dp,
            progressList = listOf(
                ProgressItem(
                    tag = "Calories",
                    progressColor = Color(0xFF00D4D6),
                    trackColor = Color(0xFF001D23),
                    progress = 70f,
                    animDuration = 1000
                ),
                ProgressItem(
                    tag = "Steps",
                    progressColor = Color(0xFF74F400),
                    trackColor = Color(0xFF002200),
                    progress = 60f,
                    animDuration = 2000
                ),
                ProgressItem(
                    tag = "Distance",
                    progressColor = Color(0xFFF45E9F),
                    trackColor = Color(0x992C0308),
                    progress = 50f,
                    animDuration = 3000
                ),

            )
        )
    }
}