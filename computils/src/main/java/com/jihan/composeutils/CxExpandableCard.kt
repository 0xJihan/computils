package com.jihan.composeutils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CxExpandableCard(
    modifier: Modifier = Modifier,
    title: String = "",
    textStyle: TextStyle = TextStyle.Default,
    animDuration: Int = 300,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    padding: PaddingValues = PaddingValues(8.dp),
    state: CxExpandableCardState = rememberCxExpandableCardState(),
    expandedContent: @Composable ColumnScope.() -> Unit,
) {
    val rotationState by animateFloatAsState(
        targetValue = if (state.expanded) 180f else 0f, label = "Icon Animation"
    )

    Card(modifier = modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = tween(
                durationMillis = animDuration, easing = LinearOutSlowInEasing
            )
        ),
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        onClick = { state.toggle() }) {
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
                    .rotate(rotationState),
                    onClick = { state.toggle() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Dropdown Arrow"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.expanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                val progress by transition.animateFloat(label = "accordion transition") { transitionState ->
                    if (transitionState == EnterExitState.Visible) 1f else 0f
                }

                state.updateProgress(progress)

                Column {
                    expandedContent()
                }
            }
        }
    }
}


//!===============================================================


@Composable
fun rememberCxExpandableCardState(
    expanded: Boolean = false,
    enabled: Boolean = true,
    onExpandedChange: ((Boolean) -> Unit)? = null,
) = remember {
    CxExpandableCardState(expanded, enabled, onExpandedChange)
}

class CxExpandableCardState(
    expanded: Boolean = false,
    var enabled: Boolean = true,
    var onExpandedChange: ((Boolean) -> Unit)? = null,
) {
    var expanded by mutableStateOf(expanded)
        private set

    private var animationProgress by mutableFloatStateOf(0f)

    fun toggle() {
        if (!enabled) return
        expanded = !expanded
        onExpandedChange?.invoke(expanded)
    }

    fun updateProgress(progress: Float) {
        animationProgress = progress
    }

    fun collapse() {
        expanded = false
    }
}

@Composable
fun rememberCxExpandableCardGroupState(
    count: Int,
    allowMultipleOpen: Boolean = false,
): AccordionGroupState {
    return remember { AccordionGroupState(count, allowMultipleOpen) }
}

class AccordionGroupState(
    count: Int,
    private val allowMultipleOpen: Boolean,
) {
    private val states = List(count) { CxExpandableCardState() }
    private var openedIndex by mutableIntStateOf(-1)

    fun getState(index: Int): CxExpandableCardState {
        val state = states[index]
        state.onExpandedChange = { isExpanded ->
            if (allowMultipleOpen) {
                if (!isExpanded && openedIndex == index) {
                    openedIndex = -1
                }
            } else {
                if (isExpanded) {
                    openedIndex = index
                    states.forEachIndexed { i, otherState ->
                        if (i != index) otherState.collapse()
                    }
                } else if (openedIndex == index) {
                    openedIndex = -1
                }
            }
        }
        return state
    }

    fun collapseAll() {
        states.forEach { it.collapse() }
        openedIndex = -1
    }

    fun expand(index: Int) {
        if (index in states.indices) {
            states[index].toggle()
        }
    }
}
