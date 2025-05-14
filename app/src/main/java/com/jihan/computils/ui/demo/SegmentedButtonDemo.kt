package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jihan.composeutils.core.Cx
import com.jihan.composeutils.ui.CxSegmentedButton
import com.jihan.composeutils.core.Gap

@Composable
fun SegmentedButtonDemo() {

    Column {

        var cx1 by remember { mutableIntStateOf(0) }
        var cx2 by remember { mutableIntStateOf(0) }
         CxSegmentedButton(
             items = listOf("Option 1", "Option 2", "Option 3"),
             currentItem = cx1,
             title = "Segmented Button",
             onSegmentSelected = {
                 cx1 = it
             }
         )

        Gap(20)

        CxSegmentedButton(
            items = listOf("Option 1", "Option 2", "Option 3"),
            currentItem = cx2,
            title = "Another Segmented Button",
            onSegmentSelected = {
                cx2 = it
            },
            color = SegmentedButtonDefaults.colors(
                activeBorderColor = Cx.blue400,
                inactiveBorderColor = Cx.blue200,
            )
        )
    }


}