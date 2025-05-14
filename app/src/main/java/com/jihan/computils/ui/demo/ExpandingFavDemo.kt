package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.CxExpandingFav
import com.jihan.composeutils.ui.CxFABItem
import com.jihan.composeutils.ui.FABState
import com.jihan.lucide_icons.lucide

@Composable
fun ExpandingFavDemo() {
    var fabState by remember { mutableStateOf<FABState>(FABState.Collapsed) }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(400.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        CxExpandingFav(
            modifier = Modifier
                .wrapContentSize(),
            fabList = listOf(
                CxFABItem(
                    iconRes = lucide.plus, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Write",

                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                ),
                CxFABItem(
                    iconRes = lucide.youtube, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Media",
                    color = Color.Black,
                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                ),
                CxFABItem(
                    iconRes = lucide.mic, // Replace with your own drawables
                    iconSize = 42.dp,
                    label = "Speak",
                    color = Color.Black,
                    labelStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {

                    }
                )
            ),
            fabText = "Create Article",
            fabTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            fabState = fabState,
            itemSpacing = 18.dp,
            onClick = {
                fabState = fabState.toggleValue()
            }
        )
    }
}