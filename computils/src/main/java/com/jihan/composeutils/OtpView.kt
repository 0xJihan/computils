package com.jihan.composeutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




@Composable
fun OtpView(
    modifier: Modifier = Modifier,
    otpText: String,
    spacedBy: Dp = 6.dp,
    showOtp: Boolean = true,
    boxSize: Dp = 42.dp,
    otpCount: Int = 6,
    showError: Boolean = false,
    textColor: Color = Color.Gray,
    textSize: TextUnit = 14.sp,
    textStyle: TextStyle = TextStyle.Default,
    backgroundColor: Color = Color.Transparent,
    focusedBoxColor: Color = Color(0xFFE74CC8),
    errorBoxColor: Color = Color.Red,
    unFocusedBoxColor: Color = Color(0xFFAEAEAE),
    onOtpTextChange: (String) -> Unit
) {
    val focus = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(
            text = otpText,
            selection = TextRange(otpText.length)
        ),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement
                    .spacedBy(spacedBy)
            ) {
                repeat(otpCount) { index ->
                    OTPView(
                        showOtp = showOtp,
                        index = index,
                        showError = showError,
                        boxSize = boxSize,
                        text = otpText,
                        textColor = textColor,
                        textSize = textSize,
                        textStyle = textStyle,
                        backgroundColor = backgroundColor,
                        focusedBoxColor = focusedBoxColor,
                        errorBoxColor = errorBoxColor,
                        unFocusedBoxColor = unFocusedBoxColor
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
            }
        )
    )
}

@Composable
private fun OTPView(
    index: Int,
    showError: Boolean = false,
    text: String,
    showOtp: Boolean = false,
    boxSize: Dp,
    textColor: Color,
    textSize: TextUnit,
    textStyle: TextStyle,
    backgroundColor: Color,
    focusedBoxColor: Color,
    errorBoxColor: Color,
    unFocusedBoxColor: Color
) {
    val isFocused = text.length == index
    var char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    if (!showOtp && char != "" && text.isNotEmpty()) {
        char = "*"
    }

    Box(
        modifier = Modifier
            .size(boxSize)
            .background(
                color = backgroundColor
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = char,
            fontSize = textSize,
            maxLines = 1,
            color = textColor,
            style = textStyle.copy(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.BottomCenter)
                .background(
                    if (isFocused) {
                        focusedBoxColor
                    } else if (showError) {
                        errorBoxColor
                    } else {
                        unFocusedBoxColor
                    }
                )
        )
    }
}

