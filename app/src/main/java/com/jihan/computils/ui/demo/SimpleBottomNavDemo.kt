package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.SimpleBottomNav
import com.jihan.composeutils.SimpleBottomNavItem
import com.jihan.lucide_icons.lucide

/*** Preview Code  Starts here. ***/

private val screen = listOf(
    SimpleBottomNavItem(
        icon = lucide.house, title = "Home"
    ), SimpleBottomNavItem(
        icon = lucide.search, title = "Search"
    ), SimpleBottomNavItem(
        icon = lucide.message_square, title = "Chat"
    ), SimpleBottomNavItem(
        icon = lucide.user_round, title = "Profile"
    )
)

@Composable
fun SimpleBottomNavDemo() {


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        SimpleBottomNav(
            screens = screen,
            height = 80.dp,
            backgroundColor = Color(0xFFF1F1F1),
            selectedColor = Color(0xFF007AFF),
            unSelectedColor = Color(0XFF89788B),
            iconSize = 28.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            )
        )
    }
}

// Simple Bottom Nav With Dash At Top
@Composable
fun SimpleBottomNavWithLineDemo() {


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        SimpleBottomNav(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            screens = screen,
            showDash = true,
            height = 80.dp,
            backgroundColor = Color(0xFF0B264F),
            selectedColor = Color(0xFFFFBB4E),
            unSelectedColor = Color(0XFFE3E3E3),
            iconSize = 28.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            ),
            shape = RoundedCornerShape(50.dp)
        )
    }
}


// Simple Bottom Nav With Dot At Bottom
@Composable
fun SimpleBottomNavWithDotDemo() {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        SimpleBottomNav(
            screens = screen,
            showDot = true,
            height = 80.dp,
            backgroundColor = Color(0xFF222222),
            selectedColor = Color(0xFF73D3BB),
            unSelectedColor = Color(0XFFFFFFFF),
            iconSize = 28.dp,
            dotBottomPadding = 3.dp,
            selectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
            ),
            unselectedTextStyle = TextStyle(
                fontFamily = FontFamily.SansSerif
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
    }
}
/*** Preview Code  Ends here. ***/