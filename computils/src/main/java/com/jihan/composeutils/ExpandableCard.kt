package com.jihan.composeutils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String = "",
    textStyle: TextStyle = TextStyle.Default,
    animDuration: Int = 300,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    padding: PaddingValues = PaddingValues(8.dp),
    expandedContent: @Composable ColumnScope.()->Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) {
            180f
        } else {
            0f
        }, label = "Icon Animation"
    )

    Card(modifier = modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = tween(
                durationMillis = animDuration, easing = LinearOutSlowInEasing
            )
        ), shape = shape, colors = colors, elevation = elevation, border = border, onClick = {
       isExpanded = isExpanded.not()
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = title,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(modifier = Modifier
                    .weight(1f)
                    .rotate(rotationState), onClick = {
                   isExpanded = isExpanded.not()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        tint = Color(0xFF333333),
                        contentDescription = "Dropdown Arrow"
                    )
                }
            }
            AnimatedVisibility (isExpanded) {
                Column {
                expandedContent()
                }
            }
        }
    }
}
