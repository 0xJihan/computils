package com.jihan.composeutils.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle


class ScaledThirdBrush(val shaderBrush: ShaderBrush): ShaderBrush() {
    override fun createShader(size: Size): Shader {
        return shaderBrush.createShader(size / 3f)
    }
}

@Composable
fun CxColorEffectText(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    colors: List<Color> = listOf()
) {
    Text(
        modifier = modifier,
        text = text,
        style = textStyle.copy(
            brush = ScaledThirdBrush(
                Brush.linearGradient(
                    colors = colors,
                    tileMode = TileMode.Repeated
                ) as ShaderBrush)
        ),
        maxLines = maxLines,
        minLines = minLines
    )
}