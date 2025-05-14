package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jihan.composeutils.core.coloredShadow
import com.jihan.composeutils.core.Gap
import com.jihan.composeutils.core.text

@Composable
fun ColoredShadowDemo() {

    Column {

    "Hello World".text.modifier(Modifier.coloredShadow(Color.Red)).make()

        Gap(10)
    "Hello World".text.modifier(Modifier.coloredShadow(
        color = Color.Blue,
        alpha = .3f,
        borderRadius = 10,
        shadowRadius = 15
    )).make()

    }
}