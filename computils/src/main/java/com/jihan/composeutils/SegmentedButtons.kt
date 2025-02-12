package com.jihan.composeutils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.unit.dp

@Composable
fun CxSegmentedButton(
    modifier: Modifier = Modifier,
    buttonArray: List<String>,
    currentItem: Int,
    title:String="",
    color: SegmentedButtonColors=SegmentedButtonDefaults.colors(),
    shape: Shape = RectangleShape,
    onSegmentSelected: (Int) -> Unit
) {

    if (title.isNotEmpty())
    Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 4.dp))
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        buttonArray.forEachIndexed { index, text ->

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