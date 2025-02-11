package com.jihan.composeutils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeonProgressLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    animDuration: Int = 800,
    color: Color = Color(0xFF99F900),
    backgroundColor: Color = Color.Black
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val angle by infiniteTransition.animateFloat(initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = animDuration
        }),
        label = "NeonProgressLoader")

    Canvas(
        modifier = Modifier
            .size(size)
            .rotate(angle)
            .then(modifier)
    ) {
        drawArcs(
            color = color, backgroundColor = backgroundColor
        )
    }
}

private fun DrawScope.drawArcs(
    color: Color, backgroundColor: Color
) {
    val topLeft = Offset(0f, 0f)
    val size = Size(size.width, size.height)

    fun drawBlur() {
        for (i in 0..20) {
            drawArc(
                brush = Brush.linearGradient(
                    listOf(
                        color.copy(alpha = i / 1200f),
                        color.copy(alpha = i / 900f),
                        color.copy(alpha = i / 900f)
                    )
                ),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = 30f + (20 - i) * 20, cap = StrokeCap.Round)
            )
        }
    }

    fun drawGradient() {
        drawArc(
            brush = Brush.linearGradient(
                listOf(
                    backgroundColor, color, color
                )
            ),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 30f, cap = StrokeCap.Round)
        )
    }

    drawBlur()
    drawGradient()
}

@Stable
fun Modifier.rotate(degrees: Float) =
    if (degrees != 0f) graphicsLayer(rotationZ = degrees) else this

