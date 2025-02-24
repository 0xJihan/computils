package com.jihan.computils

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jihan.composeutils.CenterBox
import com.jihan.composeutils.*
import com.jihan.composeutils.Gap
import com.jihan.composeutils.SimpleBottomNavItem
import com.jihan.computils.ui.theme.AppTheme
import com.jihan.lucide_icons.lucide

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ComposeUtils()

            }
        }
    }
}


@Composable
fun ComposeUtils() {
    CenterBox {

        val list = listOf(
            SimpleBottomNavItem("Home", lucide.house),
            SimpleBottomNavItem("Search", lucide.search),
            SimpleBottomNavItem("Profile", lucide.user_round),
            SimpleBottomNavItem("Settings", lucide.settings)
        )


        val stringList = remember { listOf("Item 1", "Item 2", "Item 3") }



        CxDropdown(items = list, selectedText = { it.title }) { item ->
            println("Selected: $item at index:")

        }

        Gap(30)


        CxSearchableDropdown(items = stringList, selectedText = { it }, dropdownItem = {
            Text(it) // your custom dropdown item
        }) { item ->
            // your action
        }
        Gap(30)

        CxSearchableDropdown(items = list, selectedText = { it.title }, dropdownItem = {
            Text(it.title) // your custom dropdown item
        },


            ) { item ->
            // your action
        }


    }
}



