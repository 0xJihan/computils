package com.jihan.composeutils.ui


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay
import kotlin.streams.toList

@Composable
fun CxTypewriterText(
    modifier: Modifier = Modifier,
    texts: List<String>,
    textStyle: TextStyle = TextStyle.Default,
    initialDelay: Long = 500,
    typingSpeed: Long = 160,
    delayBetweenTexts: Long = 1000,
    cursorSymbol: String = "▎",
    showCursor: Boolean = false,
    repeatTexts: Boolean = true,
    onTextComplete: (Int) -> Unit = {},
    onAllTextsComplete: () -> Unit = {}
) {
    var textIndex by remember { mutableIntStateOf(0) }
    var textToDisplay by remember { mutableStateOf("") }
    var showingCursor by remember { mutableStateOf(true) }

    val textCharsList: List<List<String>> = remember(texts) {
        texts.map { it.splitToCodePoints() }
    }

    // Cursor blinking effect
    LaunchedEffect(showCursor) {
        if (showCursor) {
            while (true) {
                showingCursor = !showingCursor
                delay(530) // Cursor blink rate
            }
        }
    }

    LaunchedEffect(texts, initialDelay, typingSpeed, delayBetweenTexts) {
        delay(initialDelay) // Initial delay before starting

        while (textIndex < textCharsList.size) {
            textCharsList[textIndex].forEachIndexed { charIndex, _ ->
                textToDisplay = textCharsList[textIndex].take(
                    n = charIndex + 1
                ).joinToString(
                    separator = ""
                )
                delay(typingSpeed)
            }

            onTextComplete(textIndex)

            if (textIndex == textCharsList.size - 1 && !repeatTexts) {
                onAllTextsComplete()
                break
            }

            delay(delayBetweenTexts)
            textIndex = if (repeatTexts) {
                (textIndex + 1) % texts.size
            } else {
                textIndex + 1
            }
        }
    }

    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(textToDisplay)
            if (showCursor && showingCursor) {
                withStyle(
                    style = SpanStyle(
                        color = textStyle.color
                    )
                ) {
                    append(cursorSymbol)
                }
            }
        },
        style = textStyle
    )
}

fun String.splitToCodePoints(): List<String> {
    return codePoints().toList().map { String(Character.toChars(it)) }
}





