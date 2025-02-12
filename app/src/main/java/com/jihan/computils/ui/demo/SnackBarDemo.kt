package com.jihan.computils.ui.demo

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.CxSnackBar
import com.jihan.composeutils.CxSnackBarPosition
import com.jihan.composeutils.CxSnackBarState
import com.jihan.lucide_icons.lucide

@Composable
fun CustomSnackBarDemo() {
    val topSnackBarState = remember{
        CxSnackBarState()
    }
    val bottomSnackBarState = remember{
        CxSnackBarState()
    }
    val floatingSnackBarState = remember{
        CxSnackBarState()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { topSnackBarState.addMessage("This is a Top SnackBar") }
            ) {
                Text(text = "Top SnackBar")
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { bottomSnackBarState.addMessage("This is a Bottom SnackBar") }
            ) {
                Text(text = "Bottom SnackBar")
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4579FF)
                ),
                onClick = { floatingSnackBarState.addMessage("This is a Floating SnackBar") }
            ) {
                Text(text = "Floating SnackBar")
            }
        }

        CxSnackBar(
            state = topSnackBarState,
            duration = 3000L,
            backgroundColor = Color(0xFF18B661),
            iconColor = Color(0xFFEEEEEE),
            iconSize = 28.dp,
            position = CxSnackBarPosition.Top,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = com.jihan.lucide_icons.R.drawable.check_check, // Replace with your image
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Top
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Top
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        )

        CxSnackBar(
            state = bottomSnackBarState,
            duration = 3000L,
            position = CxSnackBarPosition.Bottom,
            backgroundColor = Color(0xFFE85039),
            iconColor = Color(0xFFEEEEEE),
            iconSize = 28.dp,
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp,
            iconRes = lucide.circle_check, // Replace with your image
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = Alignment.Bottom
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards = Alignment.Bottom
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        )

        CxSnackBar(
            state = floatingSnackBarState,
            position = CxSnackBarPosition.Float,
            duration = 3000L,
            iconRes = lucide.check, // Replace with your image
            backgroundColor = Color(0xFF69C3EB),
            iconColor = Color.White,
            iconSize = 28.dp,
            enterAnimation = fadeIn(),
            exitAnimation = fadeOut(),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            ),
            verticalPadding = 16.dp,
            horizontalPadding = 12.dp
        )
    }
}