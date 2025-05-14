package com.jihan.computils.ui.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.Contact
import com.jihan.composeutils.ui.CxContactList
import com.jihan.composeutils.core.toast
import com.jihan.lucide_icons.lucide

@Composable
fun ContactListDemo() {

    val context = LocalContext.current
    val names = "Ali, Aria, Asher, Benjamin, Bianca, Beatrix, Caleb, Clara, Chloe, Daniel, Delilah, Diana, Elijah, Emily, Evangeline, Finnegan, Freya, Fiona, Gabriel, Grace, Gwendolyn, Henry, Harper, Helena, Isaac, Isabella, Ivy, James, Jasmine, Juliette, Kieran, Katherine, Kai, Liam, Lily, Luna, Mason, Madison, Maya, Noah, Natalie, Nora, Oliver, Olivia, Octavia, Peter, Penelope, Phoebe, Quentin, Quinn, Ryan, Rachel, Rose, Samuel, Sophia, Scarlett, Thomas, Taylor, Thea, Uriah, Uma, Victor, Violet, Vanessa, William, Willow, Wren, Xavier, Xena, Yasmine, Yara, Zachary, Zoe, Zara"
    val contacts = names.split(", ").map { Contact(it,lucide.user_round) }

    CxContactList(
        contacts = contacts,
        scrollingBubbleColor = Color(0xFF73D3BB),
        scrollingBubbleTextStyle = MaterialTheme.typography.titleLarge.copy(
            fontSize = 18.sp,
            color = Color.White,
        ),
        alphabetScrollerTextStyle = TextStyle(
            fontSize = 11.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFFB5B5B5),
            textAlign = TextAlign.Center,
        ),
        charStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF8B37F7),
        ),
        nameTextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF333333),
        ),
        iconSize = 46.dp
    ){
        it.fullName.toast(context)
    }
}