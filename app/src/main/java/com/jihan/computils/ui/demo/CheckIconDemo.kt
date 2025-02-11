package com.jihan.computils.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jihan.composeutils.IconCheckBox
import com.jihan.composeutils.IconCheckBoxGrouped
import com.jihan.composeutils.IconCheckBoxWithLabel
import com.jihan.composeutils.R
import com.jihan.lucide_icons.lucide

/*** Simple Checkbox ***/
@Composable
fun IconCheckBoxDemo() {
    val checkedState = remember { mutableStateOf(false) }

    IconCheckBox(
        isChecked = checkedState.value,
        checkedIcon = lucide.smile,
        unCheckedIcon = lucide.angry,
        onCheckedChange = {
            checkedState.value = !checkedState.value
        }
    )
}

/*** Checkbox With Label ***/
@Composable
fun CheckboxWithLabelDemo() {
    val checkedState = remember { mutableStateOf(false) }

    IconCheckBoxWithLabel(
        isChecked = checkedState.value,
        checkedIcon = lucide.smile,
        unCheckedIcon = lucide.annoyed,
        onCheckedChange = {
            checkedState.value = !checkedState.value
        }
    )
}

/*** Grouped Checkbox ***/
@Composable
fun GroupedCheckboxDemo() {
    IconCheckBoxGrouped(
        options = listOf("Option 1", "Option 2", "Option 3"),
        checkedIcon = lucide.check,
        unCheckedIcon = lucide.dot,
        onSelectionChange = {

        }
    )
}