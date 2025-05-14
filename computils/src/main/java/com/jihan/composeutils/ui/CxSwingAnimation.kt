package com.jihan.composeutils.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CxSwingAnimation(
    modifier: Modifier = Modifier,
    boardImage: ImageVector,
    textContent: @Composable () -> Unit,
    swingDuration: Int = 1000,
    boardPadding: PaddingValues = PaddingValues(all = 0.dp),
    angleOffset: Float = 10f,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = -angleOffset, targetValue = angleOffset, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = swingDuration, easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse,
        ), label = ""
    )
    Box {
        Box(modifier = modifier
            .padding(boardPadding)
            .rotate(angle)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .offset(xOffset, yOffset), contentAlignment = Alignment.Center) {
            Image(
                imageVector = boardImage,
                contentDescription = "cardBoard",
                modifier = Modifier.wrapContentSize()
            )
            textContent()
        }
    }
}



