package com.jihan.computils.ui.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.CxTypewriterText


@Composable
fun TypewriterTextDemo() {

        val texts = listOf(
            "Welcome to my app!",
            "This is a cool typing effect",
            "With multiple features..."
        )

        CxTypewriterText(
            texts = texts,
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            ),
            typingSpeed = 100,
            delayBetweenTexts = 2000,
            onTextComplete = { index ->
                println("Completed typing text #${index + 1}")
            },
            onAllTextsComplete = {
                println("All texts have been displayed!")
            }
        )

}