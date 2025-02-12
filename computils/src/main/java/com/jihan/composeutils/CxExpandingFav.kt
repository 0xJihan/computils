package com.jihan.composeutils

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CxExpandingFav(
    modifier: Modifier = Modifier,
    fabText: String,
    fabList: List<CxFABItem>,
    fabSize: Dp = 60.dp,
    fabIcon: ImageVector = Icons.Rounded.Add,
    fabIconSize: Dp = 28.dp,
    fabTextStyle: TextStyle = TextStyle.Default,
    fabShape: Shape = RoundedCornerShape(18.dp),
    fabColor: Color = Color(0xFFEAE0FF),
    fabElevation: Dp = 12.dp,
    itemSpacing: Dp = 16.dp,
    backgroundColor: Color = Color(0xFFECECEC),
    backgroundShape: Shape = RoundedCornerShape(12.dp),
    verticalPadding: Dp = 22.dp,
    expandedFabSize: FABSize = FABSize(width = 200.dp, height = 58.dp),
    fabState: FABState = FABState.Collapsed,
    onClick: () -> Unit
) {
    val expandedFabWidth by animateDpAsState(
        targetValue = if (fabState.isExpanded()) {
            expandedFabSize.width
        } else {
            fabSize
        }, animationSpec = spring(dampingRatio = 3f), label = "FAB Width Animation"
    )
    val expandedFabHeight by animateDpAsState(
        targetValue = if (fabState.isExpanded()) {
            expandedFabSize.height
        } else {
            fabSize
        }, animationSpec = spring(dampingRatio = 3f), label = "FAB Height Animation"
    )

    val iconHeight = fabList[0].iconSize
    val bottomPadding = verticalPadding.minus(itemSpacing.minus(verticalPadding))
    val expandedHeight by animateDpAsState(
        targetValue = if (fabState.isExpanded()) {
            ((iconHeight) * fabList.size) + (itemSpacing * fabList.size) + (bottomPadding)
        } else {
            0.dp
        }, animationSpec = spring(dampingRatio = 3f), label = "Height Animation"
    )


    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .size(
                    width = expandedFabWidth, height = expandedHeight
                )
                .background(
                    color = backgroundColor, shape = backgroundShape
                )
                .padding(top = verticalPadding),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            items(fabList.size) { index ->
                CxItem(
                    modifier = Modifier, item = fabList[index]
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight),
            shape = fabShape,
            containerColor = fabColor,
            elevation = FloatingActionButtonDefaults.elevation(
                fabElevation
            ),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = fabIcon,
                    contentDescription = null,
                    modifier = Modifier.size(fabIconSize)
                )

                if (fabState.isExpanded()) {
                    Text(
                        text = fabText, softWrap = false, style = fabTextStyle
                    )
                }
            }
        }
    }
}

@Composable
fun CxItem(
    modifier: Modifier, item: CxFABItem
) {
    require(!((item.color) != null && (item.brush) != null)) {
        "Only one of color or brush can be specified, not both."
    }

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(start = 12.dp, end = 12.dp)
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
            item.onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(item.horizontalSpacing)
    ) {
        Image(
            painter = painterResource(item.iconRes),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(item.iconSize)
                .clip(item.shape)
                .then(
                    if (item.brush != null) {
                        Modifier.background(brush = item.brush)
                    } else {
                        Modifier
                    }
                )
        )

        Text(
            text = item.label, style = item.labelStyle
        )
    }
}

data class CxFABItem(
    @DrawableRes val iconRes: Int,
    val iconSize: Dp,
    val label: String = "",
    val labelStyle: TextStyle = TextStyle.Default,
    val labelWidth: Dp = 40.dp,
    val labelPaddingValues: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 16.dp),
    val labelBackgroundColor: Color = Color.Black,
    val horizontalSpacing: Dp = 18.dp,
    val color: Color? = null,
    val brush: Brush? = null,
    val shape: Shape = CircleShape,
    val onClick: () -> Unit
)

data class FABSize(
    val width: Dp, val height: Dp
)

sealed class FABState {
    data object Collapsed : FABState()
    data object Expand : FABState()

    fun isExpanded() = this == Expand

    fun toggleValue() = if (isExpanded()) {
        Collapsed
    } else {
        Expand
    }
}

