package com.jihan.composeutils.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.core.Cx
import com.jihan.composeutils.core.ImageSource
import com.jihan.composeutils.core.painter
import java.util.Timer
import kotlin.concurrent.schedule


@Composable
fun CxSnackBar(
    modifier: Modifier = Modifier,
    state: CxSnackBarState,
    position: CxSnackBarPosition = CxSnackBarPosition.Bottom,
    duration: Long = 3000L,
    iconRes: ImageSource = ImageSource.Vector(SnackbarIcon.IcWarning), // Replace with your image
    iconSize: Dp = 24.dp,
    backgroundColor: Color = Color.Gray,
    iconColor: Color = Color.White,
    textStyle: TextStyle = TextStyle.Default,
    enterAnimation: EnterTransition = expandVertically(
        animationSpec = tween(delayMillis = 300),
        expandFrom = when (position) {
            is CxSnackBarPosition.Top -> Alignment.Top
            is CxSnackBarPosition.Bottom -> Alignment.Bottom
            is CxSnackBarPosition.Float -> Alignment.CenterVertically
        }
    ),
    exitAnimation: ExitTransition = shrinkVertically(
        animationSpec = tween(delayMillis = 300),
        shrinkTowards = when (position) {
            is CxSnackBarPosition.Top -> Alignment.Top
            is CxSnackBarPosition.Bottom -> Alignment.Bottom
            is CxSnackBarPosition.Float -> Alignment.CenterVertically
        }
    ),
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        var showSnackBar by remember { mutableStateOf(false) }
        val message by rememberUpdatedState(newValue = state.message.value)

        DisposableEffect(
            key1 = state.updateState
        ) {
            showSnackBar = true
            val timer = Timer("Animation Timer", true)
            timer.schedule(duration) {
                showSnackBar = false
            }
            onDispose {
                timer.cancel()
                timer.purge()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = when (position) {
                        is CxSnackBarPosition.Top -> 0.dp
                        is CxSnackBarPosition.Bottom -> 0.dp
                        is CxSnackBarPosition.Float -> 24.dp
                    }
                ),
            verticalArrangement = when (position) {
                is CxSnackBarPosition.Top -> Arrangement.Top
                is CxSnackBarPosition.Bottom -> Arrangement.Bottom
                is CxSnackBarPosition.Float -> Arrangement.Bottom
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = state.isNotEmpty() && showSnackBar,
                enter = when (position) {
                    is CxSnackBarPosition.Top -> enterAnimation
                    is CxSnackBarPosition.Bottom -> enterAnimation
                    is CxSnackBarPosition.Float -> fadeIn()
                },
                exit = when (position) {
                    is CxSnackBarPosition.Top -> exitAnimation
                    is CxSnackBarPosition.Bottom -> exitAnimation
                    is CxSnackBarPosition.Float -> fadeOut()
                }
            ) {
                SnackBarItem(
                    message = message,
                    position = position,
                    backgroundColor = backgroundColor,
                    textStyle = textStyle,
                    verticalPadding = verticalPadding,
                    horizontalPadding = horizontalPadding,
                    iconRes = iconRes,
                    iconColor = iconColor,
                    iconSize = iconSize
                )
            }
        }
    }
}

@Composable
private fun SnackBarItem(
    message: String?,
    position: CxSnackBarPosition,
    backgroundColor: Color,
    iconColor: Color,
    iconSize: Dp,
    textStyle: TextStyle,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    iconRes: ImageSource
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(
                fraction = when (position) {
                    is CxSnackBarPosition.Top -> 1f
                    is CxSnackBarPosition.Bottom -> 1f
                    is CxSnackBarPosition.Float -> 0.8f
                }
            )
            .background(
                color = backgroundColor,
                shape = when (position) {
                    is CxSnackBarPosition.Top -> RectangleShape
                    is CxSnackBarPosition.Bottom -> RectangleShape
                    is CxSnackBarPosition.Float -> RoundedCornerShape(8.dp)
                }
            )
            .padding(vertical = verticalPadding)
            .padding(horizontal = horizontalPadding)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (iconRes) {
                is ImageSource.Res -> {
                    Icon(
                        iconRes.resId.painter(),
                        modifier = Modifier
                            .size(iconSize),
                        contentDescription = "SnackBar Icon",
                        tint = iconColor
                    )
                }

                is ImageSource.Vector -> {
                    Icon(
                        iconRes.vector,
                        modifier = Modifier
                            .size(iconSize),
                        contentDescription = "SnackBar Icon",
                        tint = iconColor
                    )
                }
            }


            Text(
                modifier = Modifier,
                text = message ?: "This is a snackbar",
                style = textStyle,
            )
        }
    }
}

sealed class CxSnackBarPosition {

    data object Top : CxSnackBarPosition()

    data object Bottom : CxSnackBarPosition()

    data object Float : CxSnackBarPosition()
}

class CxSnackBarState {

    private val _message = mutableStateOf<String?>(null)
    val message: State<String?> = _message

    var updateState by mutableStateOf(false)
        private set

    fun addMessage(message: String) {
        _message.value = message
        updateState = !updateState
    }

    fun isNotEmpty(): Boolean {
        return _message.value != null
    }
}


object CxSnackBar {
    @Composable
    fun Success(
        state: CxSnackBarState,
        position: CxSnackBarPosition = CxSnackBarPosition.Top
    ) {
        CxSnackBar(
            state = state,
            duration = 3000L,
            position = position,
            backgroundColor = Cx.green600,
            iconColor = Color(0xFFEEEEEE),
            iconSize = 28.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = ImageSource.Vector(
                SnackbarIcon.IcSuccess
            ),
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Bottom
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Bottom
            ),
            textStyle = TextStyle.Default.copy(
                color = Color.White,
                fontSize = 16.sp
            )
        )
    }


    @Composable
    fun Error(
        state: CxSnackBarState,
        position: CxSnackBarPosition = CxSnackBarPosition.Top
    ) {
        CxSnackBar(
            state = state,
            duration = 3000L,
            position = position,
            backgroundColor = Cx.red600,
            iconColor = Color.White,
            iconSize = 28.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = ImageSource.Vector(
                SnackbarIcon.IcError
            ),
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Bottom
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Bottom
            ),
            textStyle = TextStyle.Default.copy(
                color = Color.White,
                fontSize = 16.sp
            )
        )
    }


    @Composable
    fun Warning(
        state: CxSnackBarState,
        position: CxSnackBarPosition = CxSnackBarPosition.Top
    ) {
        CxSnackBar(
            state = state,
            duration = 3000L,
            position = position,
            backgroundColor = Cx.yellow500,
            iconColor = Color.White,
            iconSize = 28.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = ImageSource.Vector(
                SnackbarIcon.IcWarning
            ),
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Bottom
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Bottom
            ),
            textStyle = TextStyle.Default.copy(
                color = Color.White,
                fontSize = 16.sp
            )
        )
    }

}

@Composable
fun rememberCxSnackBarState(): CxSnackBarState {
    return remember {
        CxSnackBarState()
    }
}


object SnackbarIcon

// ICON

val SnackbarIcon.IcSuccess: ImageVector
    get() {
        if (_icSuccess != null) {
            return _icSuccess!!
        }
        _icSuccess =
            Builder(
                name = "IcSuccess", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f,
            ).apply {
                path(
                    fill = SolidColor(Cx.green600), stroke = SolidColor(Cx.green600),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd,
                ) {
                    moveTo(256.0f, 42.667f)
                    curveTo(138.18f, 42.667f, 42.667f, 138.18f, 42.667f, 256.0f)
                    curveTo(42.667f, 373.82f, 138.18f, 469.333f, 256.0f, 469.333f)
                    curveTo(373.82f, 469.333f, 469.333f, 373.82f, 469.333f, 256.0f)
                    curveTo(469.333f, 138.18f, 373.82f, 42.667f, 256.0f, 42.667f)
                    close()
                    moveTo(256.0f, 426.667f)
                    curveTo(161.895f, 426.667f, 85.333f, 350.105f, 85.333f, 256.0f)
                    curveTo(85.333f, 161.895f, 161.895f, 85.333f, 256.0f, 85.333f)
                    curveTo(350.105f, 85.333f, 426.667f, 161.895f, 426.667f, 256.0f)
                    curveTo(426.667f, 350.105f, 350.106f, 426.667f, 256.0f, 426.667f)
                    close()
                    moveTo(336.336f, 179.781f)
                    lineTo(366.503f, 209.948f)
                    lineTo(234.667f, 342.336f)
                    lineTo(155.583f, 263.252f)
                    lineTo(185.75f, 233.086f)
                    lineTo(234.667f, 282.003f)
                    lineTo(336.336f, 179.781f)
                    close()
                }
            }
                .build()
        return _icSuccess!!
    }

private var _icSuccess: ImageVector? = null


val SnackbarIcon.IcWarning: ImageVector
    get() {
        if (_icWarning != null) {
            return _icWarning!!
        }
        _icWarning =
            Builder(
                name = "IcWarning", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f,
            ).apply {
                path(
                    fill = SolidColor(Cx.yellow500), stroke = SolidColor(Cx.yellow500),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd,
                ) {
                    moveTo(278.313f, 48.296f)
                    curveTo(284.928f, 52.075f, 290.41f, 57.557f, 294.189f, 64.172f)
                    lineTo(476.667f, 383.508f)
                    curveTo(488.358f, 403.967f, 481.25f, 430.03f, 460.791f, 441.722f)
                    curveTo(454.344f, 445.405f, 447.047f, 447.343f, 439.622f, 447.343f)
                    lineTo(74.667f, 447.343f)
                    curveTo(51.103f, 447.343f, 32.0f, 428.241f, 32.0f, 404.677f)
                    curveTo(32.0f, 397.251f, 33.938f, 389.955f, 37.622f, 383.508f)
                    lineTo(220.099f, 64.172f)
                    curveTo(231.79f, 43.713f, 257.854f, 36.605f, 278.313f, 48.296f)
                    close()
                    moveTo(257.144f, 85.341f)
                    lineTo(74.667f, 404.677f)
                    lineTo(439.622f, 404.677f)
                    lineTo(257.144f, 85.341f)
                    close()
                    moveTo(256.0f, 314.667f)
                    curveTo(271.238f, 314.667f, 282.667f, 325.931f, 282.667f, 341.291f)
                    curveTo(282.667f, 356.651f, 271.238f, 367.915f, 256.0f, 367.915f)
                    curveTo(240.416f, 367.915f, 229.333f, 356.651f, 229.333f, 340.949f)
                    curveTo(229.333f, 325.931f, 240.762f, 314.667f, 256.0f, 314.667f)
                    close()
                    moveTo(277.333f, 149.333f)
                    lineTo(277.333f, 277.333f)
                    lineTo(234.667f, 277.333f)
                    lineTo(234.667f, 149.333f)
                    lineTo(277.333f, 149.333f)
                    close()
                }
            }
                .build()
        return _icWarning!!
    }

private var _icWarning: ImageVector? = null


val SnackbarIcon.IcError: ImageVector
    get() {
        if (_icError != null) {
            return _icError!!
        }
        _icError =
            Builder(
                name = "IcError", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f,
            ).apply {
                path(
                    fill = SolidColor(Cx.red600), stroke = SolidColor(Cx.red600),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd,
                ) {
                    moveTo(256.0f, 42.667f)
                    curveTo(373.61f, 42.667f, 469.333f, 138.39f, 469.333f, 256.0f)
                    curveTo(469.333f, 373.61f, 373.61f, 469.333f, 256.0f, 469.333f)
                    curveTo(138.39f, 469.333f, 42.667f, 373.61f, 42.667f, 256.0f)
                    curveTo(42.667f, 138.39f, 138.39f, 42.667f, 256.0f, 42.667f)
                    close()
                    moveTo(256.0f, 85.333f)
                    curveTo(161.541f, 85.333f, 85.333f, 161.541f, 85.333f, 256.0f)
                    curveTo(85.333f, 350.459f, 161.541f, 426.667f, 256.0f, 426.667f)
                    curveTo(350.459f, 426.667f, 426.667f, 350.459f, 426.667f, 256.0f)
                    curveTo(426.667f, 161.541f, 350.459f, 85.333f, 256.0f, 85.333f)
                    close()
                    moveTo(256.0f, 314.709f)
                    curveTo(271.238f, 314.709f, 282.667f, 325.973f, 282.667f, 341.333f)
                    curveTo(282.667f, 356.693f, 271.238f, 367.957f, 256.0f, 367.957f)
                    curveTo(240.416f, 367.957f, 229.333f, 356.693f, 229.333f, 340.992f)
                    curveTo(229.333f, 325.973f, 240.762f, 314.709f, 256.0f, 314.709f)
                    close()
                    moveTo(277.333f, 128.0f)
                    lineTo(277.333f, 277.333f)
                    lineTo(234.667f, 277.333f)
                    lineTo(234.667f, 128.0f)
                    lineTo(277.333f, 128.0f)
                    close()
                }
            }
                .build()
        return _icError!!
    }

private var _icError: ImageVector? = null
