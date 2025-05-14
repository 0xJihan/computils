package com.jihan.composeutils.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CxSegmentedButton(
    items: List<String>,
    currentItem: Int,
    title: String = "",
    titleTextStyle:TextStyle=MaterialTheme.typography.titleMedium,
    modifier: Modifier = Modifier,
    color: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    shape: Shape = RectangleShape,
    onSegmentSelected: (Int) -> Unit
) {

    if (title.isNotEmpty()) Text(
        title,
        style = titleTextStyle,
        modifier = Modifier.padding(vertical = 4.dp)
    )
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        items.forEachIndexed { index, text ->

            SegmentedButton(
                selected = index == currentItem,
                onClick = { onSegmentSelected(index) },
                shape = shape,
                colors = color

            ) {
                Text(text)
            }
        }

    }
}