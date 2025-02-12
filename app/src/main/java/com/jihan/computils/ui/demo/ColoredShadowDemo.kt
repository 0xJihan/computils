package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.coloredShadow
import com.jihan.composeutils.gap
import com.jihan.composeutils.text

@Composable
fun ColoredShadowDemo() {

    Column {

    "Hello World".text.modifier(Modifier.coloredShadow(Color.Red)).make()

        gap(10)
    "Hello World".text.modifier(Modifier.coloredShadow(
        color = Color.Blue,
        alpha = .3f,
        borderRadius = 10,
        shadowRadius = 15
    )).make()

    }
}