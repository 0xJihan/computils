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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.R
import com.jihan.composeutils.core.Cx
import java.util.Timer
import kotlin.concurrent.schedule



@Composable
fun CxSnackBar(
    modifier: Modifier = Modifier,
    state: CxSnackBarState,
    position: CxSnackBarPosition = CxSnackBarPosition.Bottom,
    duration: Long = 3000L,
    iconRes: Int = R.drawable.info, // Replace with your image
    iconSize: Dp = 24.dp,
    backgroundColor: Color = Color.Gray,
    iconColor: Color = Color.White,
    textStyle: TextStyle = TextStyle.Default,
    enterAnimation: EnterTransition = expandVertically(
        animationSpec = tween(delayMillis = 300),
        expandFrom = when(position) {
            is CxSnackBarPosition.Top -> Alignment.Top
            is CxSnackBarPosition.Bottom -> Alignment.Bottom
            is CxSnackBarPosition.Float -> Alignment.CenterVertically
        }
    ),
    exitAnimation: ExitTransition = shrinkVertically(
        animationSpec = tween(delayMillis = 300),
        shrinkTowards =  when(position) {
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
            verticalArrangement = when(position) {
                is CxSnackBarPosition.Top -> Arrangement.Top
                is CxSnackBarPosition.Bottom -> Arrangement.Bottom
                is CxSnackBarPosition.Float -> Arrangement.Bottom
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = state.isNotEmpty() && showSnackBar,
                enter = when(position) {
                    is CxSnackBarPosition.Top -> enterAnimation
                    is CxSnackBarPosition.Bottom -> enterAnimation
                    is CxSnackBarPosition.Float -> fadeIn()
                },
                exit = when(position) {
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
fun SnackBarItem(
    message: String?,
    position: CxSnackBarPosition,
    backgroundColor: Color,
    iconColor: Color,
    iconSize: Dp,
    textStyle: TextStyle,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    iconRes: Int
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
            Icon(
                modifier = Modifier
                    .size(iconSize),
                painter = painterResource(id = iconRes),
                contentDescription = "SnackBar Icon",
                tint = iconColor
            )

            Text(
                modifier = Modifier,
                text = message ?: "This is a snackbar",
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

sealed class CxSnackBarPosition {

    data object Top: CxSnackBarPosition()

    data object Bottom: CxSnackBarPosition()

    data object Float: CxSnackBarPosition()
}

class CxSnackBarState {

    private val _message = mutableStateOf<String?>(null)
    val message: State<String?> = _message

    var updateState by  mutableStateOf(false)
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
        position: CxSnackBarPosition= CxSnackBarPosition.Top
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
            iconRes = R.drawable.check, // Replace with your image
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
        position: CxSnackBarPosition= CxSnackBarPosition.Top
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
            iconRes = R.drawable.x, // Replace with your image
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
        position: CxSnackBarPosition= CxSnackBarPosition.Top
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
            iconRes = R.drawable.info, // Replace with your image
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
fun rememberCxSnackBarState() : CxSnackBarState {
    return remember {
        CxSnackBarState()
    }
}

