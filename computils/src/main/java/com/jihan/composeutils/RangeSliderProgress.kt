package com.jihan.composeutils

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RangeSliderWithPin(
    modifier: Modifier = Modifier,
    trackThickness: Dp = 10.dp,
    trackColor: Color = Color.Red,
    trackInActiveColor: Color = Color.Black,
    startPinCircleColor: Color = Color.Red,
    endPinCircleColor: Color = Color.Red,
    startPinColor: Color = Color.Black,
    endPinColor: Color = Color.Black,
    pinCircleSize: Dp = 10.dp,
    startPinTextStyle: TextStyle = TextStyle.Default,
    endPinTextStyle: TextStyle = TextStyle.Default,
    startProgress: Float = 10f,
    endProgress: Float = 20f,
    pinSpacing: Dp = 10.dp,
    pinWidth: Dp = 50.dp,
    pinHeight: Dp = 50.dp,
    onProgressChanged: (startProgressValue: Float, endProgressValue: Float) -> Unit
) {
    val startProgressLimit = startProgress.coerceIn(minimumValue = 0f, maximumValue = 100f)
    val progressStart = (0f..100f).convert(startProgressLimit, 0f..1f)

    val endProgressLimit = endProgress.coerceIn(minimumValue = 0f, maximumValue = 100f)
    val progressEnd = (0f..100f).convert(endProgressLimit, 0f..1f)

    val circleRadiusInPx = with(LocalDensity.current) {
        pinCircleSize.toPx()
    }

    var startProgressValue by remember {
        mutableFloatStateOf(progressStart)
    }
    var endProgressValue by remember {
        mutableFloatStateOf(progressEnd)
    }

    var width by remember {
        mutableFloatStateOf(0f)
    }

    var height by remember {
        mutableFloatStateOf(0f)
    }

    var leftPinDragging by remember {
        mutableStateOf(false)
    }

    var rightPinDragging by remember {
        mutableStateOf(false)
    }

    val leftPinOverlapping by remember {
        derivedStateOf { mutableStateOf(false) }
    }

    var leftPinOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var rightPinOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val scaleAnim1 by animateFloatAsState(
        targetValue = if (leftPinDragging) {
            2f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val scaleAnim2 by animateFloatAsState(
        targetValue = if (rightPinDragging) {
            2f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val tooltipAnim1 by animateFloatAsState(
        targetValue = if (leftPinOverlapping.value) {
            -180f
        } else {
            0f
        },
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val path = remember {
        Path()
    }

    val textMeasurer = rememberTextMeasurer()


    Canvas(
        modifier = modifier
            .height(trackThickness)
            .pointerInteropFilter(
                onTouchEvent = { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val x = motionEvent.x
                            val y = motionEvent.y
                            val dis1 = sqrt(
                                (x - leftPinOffset.x).pow(2) + (y - leftPinOffset.y).pow(2)
                            )
                            val dis2 = sqrt(
                                (x - rightPinOffset.x).pow(2) + (y - rightPinOffset.y).pow(
                                    2
                                )
                            )

                            if (dis1 < circleRadiusInPx) {
                                leftPinDragging = true
                            } else if (dis2 < circleRadiusInPx) {
                                rightPinDragging = true
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val x = motionEvent.x

                            if (leftPinDragging) {
                                startProgressValue = if (x <= 0) {
                                    0f
                                } else if (x >= width * endProgressValue) {
                                    endProgressValue
                                } else {
                                    x / width
                                }
                                leftPinOffset =
                                    leftPinOffset.copy(x = width * startProgressValue)
                            } else if (rightPinDragging) {
                                endProgressValue = if (x >= width) {
                                    1f
                                } else if (x <= width * startProgressValue) {
                                    startProgressValue
                                } else {
                                    x / width
                                }
                                rightPinOffset =
                                    rightPinOffset.copy(x = width * endProgressValue)
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            leftPinDragging = false
                            rightPinDragging = false
                            onProgressChanged(startProgressValue, endProgressValue)
                        }
                    }
                    true
                }
            )
            .onGloballyPositioned {
                leftPinOffset =
                    Offset(x = it.size.width * startProgressValue, y = it.size.height / 2f)
                rightPinOffset =
                    Offset(x = it.size.width * endProgressValue, y = it.size.height / 2f)
            }
    ) {
        width = this.size.width
        height = this.size.height

        drawRoundRect(
            color = trackInActiveColor,
            cornerRadius = CornerRadius(32f, 32f),
            topLeft = Offset(x = 0f, y = trackThickness.toPx() / 4f),
            size = Size(width = width, height = trackThickness.toPx() / 2f)
        )

        //draw inner rect (between two circles)
        drawRect(
            color = trackColor,
            topLeft = Offset(x = width * startProgressValue, y = 0f),
            size = Size(width = width * (endProgressValue - startProgressValue), height = height)
        )

        //draw start pin circle
        scale(scaleAnim1, pivot = leftPinOffset) {
            drawCircle(
                color = startPinCircleColor.copy(alpha = 0.2f),
                radius = pinCircleSize.toPx(),
                center = leftPinOffset
            )
        }
        drawCircle(
            color = startPinCircleColor,
            radius = pinCircleSize.toPx(),
            center = leftPinOffset
        )

        //draw end pin circle
        scale(scaleAnim2, pivot = rightPinOffset) {
            drawCircle(
                color = endPinCircleColor.copy(alpha = 0.2f),
                radius = pinCircleSize.toPx(),
                center = rightPinOffset
            )
        }
        drawCircle(
            color = endPinCircleColor,
            radius = pinCircleSize.toPx(),
            center = rightPinOffset
        )

        // draw start pin
        val leftL = leftPinOffset.x - pinWidth.toPx() / 2f
        val topL = leftPinOffset.y - pinSpacing.toPx() -
                circleRadiusInPx - pinHeight.toPx()

        val leftR = rightPinOffset.x - pinWidth.toPx() / 2f
        val topR = rightPinOffset.y - pinSpacing.toPx() -
                circleRadiusInPx - pinHeight.toPx()

        if (leftPinDragging || rightPinDragging) {
            leftPinOverlapping.value = (leftL + pinWidth.toPx()) >= leftR
        }
        rotate(tooltipAnim1, pivot = leftPinOffset) {
            drawPath(
                path.apply {
                    reset()
                    addRoundRect(
                        RoundRect(
                            left = leftL,
                            top = topL,
                            right = leftL + pinWidth.toPx(),
                            bottom = topL + pinHeight.toPx(),
                            cornerRadius = CornerRadius(x = 15f, y = 15f)
                        )
                    )
                    moveTo(
                        x = leftPinOffset.x - 8.dp.toPx(),
                        y = leftPinOffset.y - circleRadiusInPx - pinSpacing.toPx()
                    )
                    relativeLineTo(8.dp.toPx(), 8.dp.toPx())
                    relativeLineTo(8.dp.toPx(), -8.dp.toPx())
                    close()
                },
                color = startPinColor
            )
        }

        // draw end pin
        drawPath(
            path.apply {
                reset()
                addRoundRect(
                    RoundRect(
                        left = leftR,
                        top = topR,
                        right = leftR + pinWidth.toPx(),
                        bottom = topR + pinHeight.toPx(),
                        cornerRadius = CornerRadius(x = 15f, y = 15f)
                    )
                )
                moveTo(
                    x = rightPinOffset.x - 8.dp.toPx(),
                    y = rightPinOffset.y - circleRadiusInPx - pinSpacing.toPx()
                )
                relativeLineTo(8.dp.toPx(), 8.dp.toPx())
                relativeLineTo(8.dp.toPx(), -8.dp.toPx())
                close()
            },
            color = endPinColor
        )

        val textLeft = (startProgressValue * 100).roundToInt().toString()
        var textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(textLeft),
            style = startPinTextStyle
        )
        var textSize = textLayoutResult.size

        rotate(tooltipAnim1, pivot = leftPinOffset) {
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = leftL + pinWidth.toPx() / 2 - textSize.width / 2,
                    y = topL + pinHeight.toPx() / 2 - textSize.height / 2
                )
            )
        }

        val textRight = (endProgressValue * 100).roundToInt().toString()
        textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(textRight),
            style = endPinTextStyle
        )
        textSize = textLayoutResult.size

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = leftR + pinWidth.toPx() / 2 - textSize.width / 2,
                y = topR + pinHeight.toPx() / 2 - textSize.height / 2
            ),
        )
    }
}

fun ClosedFloatingPointRange<Float>.convert(
    number: Float,
    target: ClosedFloatingPointRange<Float>
): Float {
    val ratio = number / (endInclusive - start)
    return ratio * (target.endInclusive - target.start)
}

