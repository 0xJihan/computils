package com.jihan.computils.ui.demo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jihan.composeutils.ExpandableCard
import com.jihan.composeutils.Gap
import com.jihan.composeutils.text

@Composable
fun ExpandableCardDemo() {

    ExpandableCard(title = "Expanable Card") {

        Gap(200)
        Text("This is the content of the card")
    }

}