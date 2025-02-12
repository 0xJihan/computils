package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.CxExposedDropdownMenu
import com.jihan.composeutils.Gap
import com.jihan.composeutils.text

@Composable
fun ExposedDropdownDemo(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val list = remember { listOf("Item 1", "Item 2", "Item 3") }
        var text by remember { mutableStateOf("") }

        text.text.make()
        Gap(30)
        CxExposedDropdownMenu(list) { string, int ->
            text = "$string\nIndex:$int"
        }



    }
}
