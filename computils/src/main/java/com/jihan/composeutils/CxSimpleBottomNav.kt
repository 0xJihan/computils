package com.jihan.composeutils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CxSimpleBottomNav(
    screens: List<SimpleBottomNavItem>,
    selectedScreen: Int,
    modifier: Modifier = Modifier,
    height: Dp = 100.dp,
    elevation: Dp = 5.dp,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Color(0xFFF1F1F1),
    selectedTextStyle: TextStyle = TextStyle.Default,
    unselectedTextStyle: TextStyle = TextStyle.Default,
    showDash: Boolean = false,
    showDot: Boolean = false,
    iconSize: Dp = 24.dp,
    dotSize: Dp = 6.dp,
    dotBottomPadding: Dp = 2.dp,
    lineWidth: Dp = 32.dp,
    lineThickness: Dp = 3.dp,
    selectedColor: Color = Color.White,
    unSelectedColor: Color = Color(0xFF18191A),
    badgeColor: Color = Color(0xFFFF3849),
    badgeTextColor: Color = Color.White,
    verticalSpacing: Dp = 5.dp,
    onSelected: (selectedIndex:Int) -> Unit = {}
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(elevation = elevation, shape = shape)
            .background(color = backgroundColor, shape = shape)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {
            for (screen in screens) {
                val isSelected = screen == screens[selectedScreen]

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    val interactionSource = remember { MutableInteractionSource() }

                    if (isSelected && showDash) {
                        Box(
                            modifier = Modifier
                                .width(lineWidth)
                                .height(lineThickness)
                                .background(
                                    color = selectedColor, shape = RoundedCornerShape(8.dp)
                                )
                                .align(Alignment.TopCenter)
                        )
                    }

                    BottomNavItem(
                        modifier = Modifier.clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                onSelected(screens.indexOf(screen))
                            },
                        screen = screen,
                        selectedTextStyle = selectedTextStyle,
                        unselectedTextStyle = unselectedTextStyle,
                        selectedColor = selectedColor,
                        unSelectedColor = unSelectedColor,
                        isSelected = isSelected,
                        iconSize = iconSize,
                        badgeCount = screen.badgeCount ?: 0,
                        badgeColor = badgeColor,
                        badgeTextColor = badgeTextColor,
                        verticalSpacing = verticalSpacing
                    )

                    if (isSelected && showDot) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = dotBottomPadding)
                                .size(dotSize)
                                .background(color = selectedColor, shape = CircleShape)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    modifier: Modifier,
    screen: SimpleBottomNavItem,
    isSelected: Boolean,
    badgeCount: Int,
    iconSize: Dp,
    selectedColor: Color,
    unSelectedColor: Color,
    badgeColor: Color,
    badgeTextColor: Color,
    selectedTextStyle: TextStyle,
    unselectedTextStyle: TextStyle,
    verticalSpacing: Dp
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        BadgedBox(badge = {
            if (badgeCount > 0) {
                Badge(
                    containerColor = badgeColor
                ) {
                    Text(
                        text = "$badgeCount", color = badgeTextColor
                    )
                }
            }
        }, content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(
                        id = screen.icon,
                    ), modifier = Modifier.size(iconSize), tint = if (isSelected) {
                        selectedColor
                    } else {
                        unSelectedColor
                    }, contentDescription = screen.title
                )

                Text(
                    text = screen.title, color = if (isSelected) {
                        selectedColor
                    } else {
                        unSelectedColor
                    }, maxLines = 1, style = if (isSelected) {
                        selectedTextStyle
                    } else {
                        unselectedTextStyle
                    }
                )
            }
        })
    }
}

data class SimpleBottomNavItem(
    val title: String, val icon: Int, val badgeCount: Int? = null
)

