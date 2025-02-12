package com.jihan.composeutils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CenterBox(
    modifier: Modifier = Modifier.fillMaxSize(), content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}


@Composable
fun ColumnScope.Gap(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))

}

@Composable
fun RowScope.Gap(size: Int) {
    Spacer(modifier = Modifier.width(size.dp))
}


class StyledText(private var text: String) {
    private var fontSize: TextUnit = 16.sp
    private var textColor: Color? = null
    private var bold: Boolean = false
    private var modifier: Modifier = Modifier
    private var maxLine = Int.MAX_VALUE
    private var maxWords = Int.MAX_VALUE
    private var alignment: TextAlignment = TextAlignment.Unspecified


    fun size(size: Int) = apply { fontSize = size.sp }
    fun color(color: Color) = apply { textColor = color }
    fun bold() = apply { bold = true }
    fun modifier(modifier: Modifier) = apply { this.modifier = modifier }
    fun upperCase() = apply { text = text.uppercase() }
    fun lowerCase() = apply { text = text.lowercase() }
    fun titleCase() = apply { text = text.titleCase() }
    fun alignment(alignment: TextAlignment) = apply { this.alignment = alignment }
    fun maxLines(maxLines: Int) = apply { maxLine = maxLines }
    fun maxWords(maxWords: Int) = apply { this.maxWords = maxWords }


    @Composable
    fun make() {
        Text(
            text = if (text.length > maxWords) text.take(maxWords) + "..." else text,
            style = TextStyle(
                fontSize = fontSize,
                color = textColor ?: MaterialTheme.colorScheme.onSurface,
                fontWeight = if (bold) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
            ),
            modifier = modifier,
            textAlign = when (alignment) {
                TextAlignment.Start -> TextAlign.Start
                TextAlignment.End -> TextAlign.End
                TextAlignment.Center -> TextAlign.Center
                TextAlignment.Left -> TextAlign.Left
                TextAlignment.Right -> TextAlign.Right
                TextAlignment.Justify -> TextAlign.Justify
                TextAlignment.Unspecified -> TextAlign.Unspecified
            },
            color = textColor ?: MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLine,
        )
    }

    enum class TextAlignment {
        Start, End, Left, Right, Center, Justify, Unspecified
    }
}

val String.text get() = StyledText(this)

fun String.titleCase(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercaseChar() }
    }
}

fun String.toast(context: Context): Unit {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}


fun Modifier.noRippleEffect(
    onClick: () -> Unit
) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

