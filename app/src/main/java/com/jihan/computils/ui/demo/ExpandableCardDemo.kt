package com.jihan.computils.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.ui.CxExpandableCard
import com.jihan.composeutils.core.Gap
import com.jihan.composeutils.ui.rememberCxExpandableCardGroupState
import com.jihan.composeutils.ui.rememberCxExpandableCardState
import com.jihan.composeutils.core.text

@Composable
fun ExpandableCardDemo() {

    Column { 
        
    // Standalone usage
    val state = rememberCxExpandableCardState()
    CxExpandableCard(
        title = "My Card",
        state = state
    ) {
        "This is the content of the card".text.make()
        Gap(10)  
        "This is the content of the card".text.make()
        Gap(10)
    }

        Gap(10)
    HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Gray))
Gap(10)
// As part of a group
    val groupState = rememberCxExpandableCardGroupState(count = 3)
    CxExpandableCard(
        title = "Card 1",
        state = groupState.getState(0)
    ) {
        "This is the content of the card".text.make()
        Gap(10)
        "This is the content of the card".text.make()
        Gap(10)
    }


        Gap(10)
        CxExpandableCard(
            title = "Card 2",
            state = groupState.getState(1)
        ) {
            "This is the content of the card".text.make()
            Gap(10)
            "This is the content of the card".text.make()
            Gap(10)
        }

        Gap(10)

        CxExpandableCard(
            title = "Card 3",
            state = groupState.getState(2)
        ) {
            "This is the content of the card".text.make()
            Gap(10)
            "This is the content of the card".text.make()
            Gap(10)
        }

}
    }
