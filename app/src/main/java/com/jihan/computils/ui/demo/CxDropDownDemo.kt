package com.jihan.computils.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jihan.composeutils.CxDropdown
import com.jihan.composeutils.CxSearchableDropdown
import com.jihan.composeutils.Gap
import com.jihan.composeutils.SimpleBottomNavItem
import com.jihan.lucide_icons.lucide

@Composable
fun CxDropDownDemo(modifier: Modifier = Modifier) {


    Column {


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

    CxSearchableDropdown(
        items = list, selectedText = { it.title },
        dropdownItem = {
            Text(it.title) // your custom dropdown item
        },


        ) { item ->
        // your action
    }
    }
}
