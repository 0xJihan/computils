package com.jihan.composeutils


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun IconCheckBox(
    modifier: Modifier = Modifier,
    checkedIcon: Int ,
    unCheckedIcon: Int ,
    size: Dp = 24.dp,
    isChecked: Boolean = false,
    onCheckedChange: () -> Unit
) {
    Icon(
        modifier = modifier
            .size(size)
            .noRippleEffect {
                onCheckedChange()
            },
        painter = painterResource(
            id = if (isChecked) {
                checkedIcon
            } else {
                unCheckedIcon
            }
        ),
        contentDescription = "Checkbox Icon"
    )
}


@Composable
fun IconCheckBoxWithLabel(
    modifier: Modifier = Modifier,
    text: String = "Checkbox Label",
    checkedIcon: Int ,
    unCheckedIcon: Int,
    size: Dp = 24.dp,
    textColor: Color = Color.Black,
    textSize: TextUnit = 14.sp,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    isChecked: Boolean = false,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(size)
                .noRippleEffect {
                    onCheckedChange()
                }
                .align(Alignment.CenterVertically),
            painter = painterResource(
                id = if (isChecked) {
                    checkedIcon
                } else {
                    unCheckedIcon
                }
            ),
            contentDescription = "Checkbox Icon"
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = text,
            color = textColor,
            fontSize = textSize,
            maxLines = maxLines,
            minLines = minLines
        )
    }
}

@Composable
fun IconCheckBoxGrouped(
    modifier: Modifier = Modifier,
    checkedIcon: Int,
    unCheckedIcon: Int ,
    textColor: Color = Color.Black,
    textSize: TextUnit = 14.sp,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    size: Dp = 24.dp,
    verticalSpacing: Dp = 22.dp,
    horizontalSpacing: Dp = 8.dp,
    options: List<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    val selectedOptions = remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(size)
                        .noRippleEffect {
                            val currentSelected = selectedOptions.value.toMutableSet()
                            if (currentSelected.contains(option)) {
                                currentSelected.remove(option)
                            } else {
                                currentSelected.add(option)
                            }
                            selectedOptions.value = currentSelected
                            onSelectionChange(currentSelected)
                        }
                        .align(Alignment.CenterVertically),
                    painter = painterResource(
                        id = if (selectedOptions.value.toMutableSet().contains(option)) {
                            checkedIcon
                        } else {
                            unCheckedIcon
                        }
                    ),
                    contentDescription = "Checkbox Icon"
                )

                Text(
                    text = option,
                    color = textColor,
                    fontSize = textSize,
                    maxLines = maxLines,
                    minLines = minLines
                )
            }
        }
    }
}



