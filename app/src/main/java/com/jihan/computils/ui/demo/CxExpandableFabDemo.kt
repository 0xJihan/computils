package com.jihan.computils.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jihan.composeutils.ui.CxExpandableFab
import com.jihan.composeutils.ui.CxFabItem
import com.jihan.lucide_icons.lucide

@Composable
fun CxExpandableFabDemo(modifier: Modifier = Modifier) {

    val itemList = remember {
        listOf(
            CxFabItem(iconResId = lucide.shopping,"Shopping"){/*handle click*/},
            CxFabItem(iconResId = lucide.ruler,"Ruler"){/*handle click*/},
            CxFabItem(iconResId = lucide.check,"Check"){/*handle click*/},
        )
    }

   CxExpandableFab(
       items = itemList,
       icon = lucide.plus,
   )


}