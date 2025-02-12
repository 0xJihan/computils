package com.jihan.composeutils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CxButton(
    text: String = "Button",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Int = 8,
    cornerRadius: Int = 4,
    loading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onLoadingContent: @Composable () -> Unit = { CxOrbitLoading(Modifier.size(24.dp)) },
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        elevation = ButtonDefaults.elevatedButtonElevation(elevation.dp),
        shape = RoundedCornerShape(cornerRadius.dp),
        enabled = enabled && !loading,
        colors = colors
    ) {
        AnimatedContent(
            targetState = loading, transitionSpec = {
                fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith fadeOut(
                    animationSpec = tween(300)
                ) + scaleOut()
            }, label = "ButtonContent"
        ) { isLoading ->
            if (isLoading) {
                onLoadingContent()
            } else {
                Text(text)
            }
        }
    }
}

@Composable
fun CxElevatedButton(
    text: String="Button",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    cornerRadius: Int = 4,
    loading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    onLoadingContent: @Composable () -> Unit = { CxOrbitLoading(Modifier.size(24.dp)) },
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius.dp),
        enabled = enabled && !loading,
        colors = colors
    ) {
        AnimatedContent(
            targetState = loading, transitionSpec = {
                fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith fadeOut(
                    animationSpec = tween(300)
                ) + scaleOut()
            }, label = "ElevatedButtonContent"
        ) { isLoading ->
            if (isLoading) {
                onLoadingContent()
            } else {
                Text(text)
            }
        }
    }
}

@Composable
fun CxOutlinedButton(
    text: String="Button",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    cornerRadius: Int = 4,
    loading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    onLoadingContent: @Composable () -> Unit = { CxOrbitLoading(Modifier.size(24.dp)) },
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius.dp),
        enabled = enabled && !loading,
        colors = colors
    ) {
        AnimatedContent(
            targetState = loading, transitionSpec = {
                fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith fadeOut(
                    animationSpec = tween(300)
                ) + scaleOut()
            }, label = "OutlinedButtonContent"
        ) { isLoading ->
            if (isLoading) {
                onLoadingContent()
            } else {
                Text(text)
            }
        }
    }
}

@Composable
fun CxTextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    onLoadingContent: @Composable () -> Unit = { CxOrbitLoading(Modifier.size(24.dp)) },
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading,
        colors = colors
    ) {
        AnimatedContent(
            targetState = loading, transitionSpec = {
                fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith fadeOut(
                    animationSpec = tween(300)
                ) + scaleOut()
            }, label = "TextButtonContent"
        ) { isLoading ->
            if (isLoading) {
                onLoadingContent()
            } else {
                Text(text)
            }
        }
    }
}

