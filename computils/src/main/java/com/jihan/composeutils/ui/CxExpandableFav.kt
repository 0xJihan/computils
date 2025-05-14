package com.jihan.composeutils.ui

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.core.Gap
import com.jihan.composeutils.core.painter


@Composable
fun CxExpandableFab(
    items: List<CxFabItem>,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = containerColor,
    mainFabSize: Int = 56,
    miniFabSize: Int = 45,
    spacing: Int = 8,
    icon:Int,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = spacing.dp)) {
                items(items.size) {
                    ItemUI(
                        item = items[it],
                        containerColor = containerColor,
                        borderColor = borderColor,
                        fabSize = miniFabSize.dp
                    )
                    if (it < items.size - 1) {
                        Gap(spacing)
                    }
                }
            }
        }

        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = containerColor,
            modifier = Modifier.size(mainFabSize.dp)
        ) {
            Icon(
                painter = icon.painter(),
                contentDescription = "Expand menu",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun ItemUI(
    item: CxFabItem, containerColor: Color, borderColor: Color, fabSize: Dp
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(2.dp, borderColor, RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = item.title, style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(
            onClick = {
                item.onClick?.invoke()
                Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.size(fabSize), containerColor = containerColor
        ) {
            Icon(
                painter = item.iconResId.painter(), contentDescription = item.title
            )
        }
    }
}

data class CxFabItem(
    @DrawableRes val iconResId: Int, val title: String, val onClick: (() -> Unit)? = null
)


