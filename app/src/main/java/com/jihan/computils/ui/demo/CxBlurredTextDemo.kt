package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.core.Cx
import com.jihan.composeutils.ui.CxBlurredText
import com.jihan.composeutils.core.Gap

@Composable
fun CxBlurredTextDemo() {
    Column {

    CxBlurredText("Hello World, I am Jihan. and I am a Software developer")
        Gap(20)
        CxBlurredText("And this is an example of a CxBlurredText text",
            TextStyle(
                fontSize = 24.sp,
                color = Cx.blue300,
                letterSpacing = 2.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Cx.red700,
                    blurRadius = 5f
                )

            )
        )
    }
}
