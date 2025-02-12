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
import com.jihan.composeutils.ExpandableCard
import com.jihan.composeutils.Gap
import com.jihan.composeutils.rememberExpandableCardGroupState
import com.jihan.composeutils.rememberExpandableCardState
import com.jihan.composeutils.text

@Composable
fun ExpandableCardDemo() {

    Column { 
        
    // Standalone usage
    val state = rememberExpandableCardState()
    ExpandableCard(
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
    val groupState = rememberExpandableCardGroupState(count = 3)
    ExpandableCard(
        title = "Card 1",
        state = groupState.getState(0)
    ) {
        "This is the content of the card".text.make()
        Gap(10)
        "This is the content of the card".text.make()
        Gap(10)
    }


        Gap(10)
        ExpandableCard(
            title = "Card 2",
            state = groupState.getState(1)
        ) {
            "This is the content of the card".text.make()
            Gap(10)
            "This is the content of the card".text.make()
            Gap(10)
        }

        Gap(10)

        ExpandableCard(
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
