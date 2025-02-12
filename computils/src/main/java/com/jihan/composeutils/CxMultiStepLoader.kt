package com.jihan.composeutils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times


@Composable
fun CxMultiStepLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    spacing: Dp = 10.dp,
    strokeWidth: Dp = 5.dp,
    progressList: List<ProgressItem> = ArrayList()
) {
    val density = LocalDensity.current
    val spacingDp = (-(2.dp / 5.dp) * strokeWidth) + spacing

    Box(
        modifier = modifier
    ) {
        var index = 0
        progressList.forEach { progressBar ->
            val progressSize = size + (density.run {
                (strokeWidth + spacingDp).toPx()
            }.dp * index)
            index += 1
            CxArcProgressBar(
                modifier = Modifier.align(Alignment.Center),
                progressColor = progressBar.progressColor,
                trackColor = progressBar.trackColor,
                progress = progressBar.progress,
                animDuration = progressBar.animDuration,
                strokeWidth = strokeWidth,
                size = progressSize
            )
        }

        Row {

        }
    }
}

@Composable
fun CxArcProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 100f,
    strokeWidth: Dp = 10.dp,
    progressColor: Color = Color(0xff00D4D6),
    trackColor: Color = Color(0xff001D23),
    animDuration: Int = 1000,
    size: Dp = 100.dp
) {
    val progressValue = progress.coerceIn(minimumValue = 0f, maximumValue = 100f)
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(animatedProgress) {
        animatedProgress.animateTo(
            targetValue = progressValue,
            animationSpec = tween(durationMillis = animDuration, easing = FastOutSlowInEasing)
        )
    }
    val sweepAngle = (animatedProgress.value / 100) * 360

    Canvas(
        modifier = modifier.size(size)
    ) {
        drawArc(
            color = trackColor,
            startAngle = 1f,
            sweepAngle = 360f,
            useCenter = true,
            style = Stroke(width = strokeWidth.toPx())
        )
        drawArc(
            color = progressColor,
            startAngle = 270f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

data class ProgressItem(
    val tag: String,
    val progressColor: Color,
    val trackColor: Color,
    val animDuration: Int = 1000,
    val progress: Float
)

