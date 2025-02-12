package com.jihan.composeutils

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Timer
import kotlin.concurrent.schedule



@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    state: CustomSnackBarState,
    position: CustomSnackBarPosition = CustomSnackBarPosition.Bottom,
    duration: Long = 3000L,
    iconRes: Int = android.R.drawable.ic_menu_help, // Replace with your image
    iconSize: Dp = 24.dp,
    backgroundColor: Color = Color.Gray,
    iconColor: Color = Color.White,
    textStyle: TextStyle = TextStyle.Default,
    enterAnimation: EnterTransition = expandVertically(
        animationSpec = tween(delayMillis = 300),
        expandFrom = when(position) {
            is CustomSnackBarPosition.Top -> Alignment.Top
            is CustomSnackBarPosition.Bottom -> Alignment.Bottom
            is CustomSnackBarPosition.Float -> Alignment.CenterVertically
        }
    ),
    exitAnimation: ExitTransition = shrinkVertically(
        animationSpec = tween(delayMillis = 300),
        shrinkTowards =  when(position) {
            is CustomSnackBarPosition.Top -> Alignment.Top
            is CustomSnackBarPosition.Bottom -> Alignment.Bottom
            is CustomSnackBarPosition.Float -> Alignment.CenterVertically
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
                        is CustomSnackBarPosition.Top -> 0.dp
                        is CustomSnackBarPosition.Bottom -> 0.dp
                        is CustomSnackBarPosition.Float -> 24.dp
                    }
                ),
            verticalArrangement = when(position) {
                is CustomSnackBarPosition.Top -> Arrangement.Top
                is CustomSnackBarPosition.Bottom -> Arrangement.Bottom
                is CustomSnackBarPosition.Float -> Arrangement.Bottom
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = state.isNotEmpty() && showSnackBar,
                enter = when(position) {
                    is CustomSnackBarPosition.Top -> enterAnimation
                    is CustomSnackBarPosition.Bottom -> enterAnimation
                    is CustomSnackBarPosition.Float -> fadeIn()
                },
                exit = when(position) {
                    is CustomSnackBarPosition.Top -> exitAnimation
                    is CustomSnackBarPosition.Bottom -> exitAnimation
                    is CustomSnackBarPosition.Float -> fadeOut()
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
    position: CustomSnackBarPosition,
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
                    is CustomSnackBarPosition.Top -> 1f
                    is CustomSnackBarPosition.Bottom -> 1f
                    is CustomSnackBarPosition.Float -> 0.8f
                }
            )
            .background(
                color = backgroundColor,
                shape = when (position) {
                    is CustomSnackBarPosition.Top -> RectangleShape
                    is CustomSnackBarPosition.Bottom -> RectangleShape
                    is CustomSnackBarPosition.Float -> RoundedCornerShape(8.dp)
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

sealed class CustomSnackBarPosition {

    data object Top: CustomSnackBarPosition()

    data object Bottom: CustomSnackBarPosition()

    data object Float: CustomSnackBarPosition()
}

class CustomSnackBarState {

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

