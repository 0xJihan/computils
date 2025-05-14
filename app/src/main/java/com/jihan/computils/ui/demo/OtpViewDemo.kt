package com.jihan.computils.ui.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.CxOtpView
import com.jihan.composeutils.core.toast

@Composable
fun OtpViewDemo() {
    var otpValue by remember {
        mutableStateOf("")
    }
    var showOtp by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CxOtpView(
            otpText = otpValue,
            otpCount = 6,
            showOtp = showOtp,
            showError = false,
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            textSize = 16.sp
        ) { text ->
            otpValue = text
        }


        Button(
            onClick = {
                showOtp = !showOtp
            }
        ) {
            Text(
                text = if (showOtp) {
                    "Hide OTP"
                } else {
                    "Show OTP"
                }
            )
        }

        val context = LocalContext.current
        AnimatedVisibility(otpValue.length==6) {
            Button(onClick = {
                "Submitted OTP: $otpValue".toast(context)
                otpValue = ""
            }) { Text("Submit") }
        }
    }
}