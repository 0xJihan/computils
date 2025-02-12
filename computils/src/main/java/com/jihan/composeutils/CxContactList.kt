package com.jihan.composeutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun CxContactList(
    contacts: List<Contact>,
    scrollingBubbleColor: Color,
    scrollingBubbleTextStyle: TextStyle,
    alphabetScrollerTextStyle: TextStyle,
    charStyle: TextStyle,
    nameTextStyle: TextStyle,
    iconSize: Dp,
) {
    val context = LocalDensity.current
    var alphabetRelativeDragYOffset: Float? by remember { mutableStateOf(null) }
    var alphabetDistanceFromTopOfScreen: Float by remember { mutableStateOf(0F) }
    val alphabetHeightInPixels = remember { with(context) { alphabetItemSize.toPx() } }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        BoxWithConstraints {
            CxContactListWithScroller(
                contacts = contacts,
                onAlphabetListDrag = { relativeDragYOffset, containerDistance ->
                    alphabetRelativeDragYOffset = relativeDragYOffset
                    alphabetDistanceFromTopOfScreen = containerDistance
                },
                alphabetScrollerTextStyle = alphabetScrollerTextStyle,
                charStyle = charStyle,
                nameTextStyle = nameTextStyle,
                iconSize = iconSize,
            )

            val yOffset = alphabetRelativeDragYOffset
            if (yOffset != null) {
                CxScrollingBubble(
                    boxConstraintMaxWidth = this.maxWidth,
                    bubbleOffsetYFloat = yOffset + alphabetDistanceFromTopOfScreen,
                    currAlphabetScrolledOn = yOffset.getIndexOfCharBasedOnYPosition(
                        alphabetHeightInPixels = alphabetHeightInPixels,
                    ),
                    bubbleColor = scrollingBubbleColor,
                    bubbleTextStyle = scrollingBubbleTextStyle
                )
            }
        }
    }
}

@Composable
fun CxContactListWithScroller(
    contacts: List<Contact>,
    alphabetScrollerTextStyle: TextStyle,
    charStyle: TextStyle,
    nameTextStyle: TextStyle,
    iconSize: Dp,
    onAlphabetListDrag: (Float?, Float) -> Unit,
) {
    val mapOfFirstLetterIndex = remember(contacts) { contacts.getFirstUniqueSeenCharIndex() }
    val alphabetHeightInPixels = with(LocalDensity.current) { alphabetItemSize.toPx() }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CxContactList(
            Modifier
                .fillMaxHeight()
                .weight(1F),
            contacts,
            lazyListState,
            mapOfFirstLetterIndex,
            charStyle = charStyle,
            nameTextStyle = nameTextStyle,
            iconSize = iconSize,
        )

        AlphabetScroller(
            onAlphabetListDrag = { relativeDragYOffset, containerDistanceFromTopOfScreen ->
                onAlphabetListDrag(relativeDragYOffset, containerDistanceFromTopOfScreen)
                coroutineScope.launch {
                    val indexOfChar = relativeDragYOffset?.getIndexOfCharBasedOnYPosition(
                        alphabetHeightInPixels = alphabetHeightInPixels,
                    )
                    mapOfFirstLetterIndex[indexOfChar]?.let {
                        lazyListState.scrollToItem(it)
                    }
                }
            },
            alphabetScrollerTextStyle = alphabetScrollerTextStyle,
        )
    }
}

@Composable
fun CxContactList(
    modifier: Modifier,
    contacts: List<Contact>,
    lazyListState: LazyListState,
    firstLetterIndexes: Map<Char, Int>,
    charStyle: TextStyle,
    nameTextStyle: TextStyle,
    iconSize: Dp,
) {
    LazyColumn(
        modifier = modifier, state = lazyListState, verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(contacts) { index, contact ->
            CxContactItem(
                item = contact,
                isAlphabeticallyFirstInCharGroup = firstLetterIndexes[contact.fullName.lowercase()
                    .first()] == index,
                charStyle = charStyle,
                nameTextStyle = nameTextStyle,
                iconSize = iconSize,
            )
        }
    }
}

@Composable
fun CxScrollingBubble(
    boxConstraintMaxWidth: Dp,
    bubbleOffsetYFloat: Float,
    currAlphabetScrolledOn: Char,
    bubbleColor: Color,
    bubbleTextStyle: TextStyle
) {
    val bubbleSize = 60.dp
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(bubbleSize)
            .offset(
                x = (boxConstraintMaxWidth - (bubbleSize + alphabetItemSize)),
                y = with(LocalDensity.current) {
                    bubbleOffsetYFloat.toDp() - (bubbleSize / 2)
                },
            ),
        color = bubbleColor,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = currAlphabetScrolledOn.uppercase(),
                style = bubbleTextStyle,
            )
        }
    }
}

@Composable
private fun AlphabetScroller(
    alphabetScrollerTextStyle: TextStyle,
    onAlphabetListDrag: (relativeDragYOffset: Float?, distanceFromTopOfScreen: Float) -> Unit,
) {
    val alphabetCharList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".map { it }
    var distanceFromTopOfScreen by remember { mutableStateOf(0F) }

    Column(
        modifier = Modifier
            .width(16.dp)
            .onGloballyPositioned {
                distanceFromTopOfScreen = it.positionInRoot().y
            }
            .pointerInput(alphabetCharList) {
                detectVerticalDragGestures(onDragStart = {
                    onAlphabetListDrag(it.y, distanceFromTopOfScreen)
                }, onDragEnd = {
                    onAlphabetListDrag(null, distanceFromTopOfScreen)
                }) { change, _ ->
                    onAlphabetListDrag(
                        change.position.y, distanceFromTopOfScreen
                    )
                }
            },
        verticalArrangement = Arrangement.Center,
    ) {
        for (i in alphabetCharList) {
            Text(
                modifier = Modifier.height(alphabetItemSize),
                text = i.toString(),
                style = alphabetScrollerTextStyle
            )
        }
    }
}

@Composable
fun CxContactItem(
    item: Contact,
    isAlphabeticallyFirstInCharGroup: Boolean,
    charStyle: TextStyle,
    nameTextStyle: TextStyle,
    iconSize: Dp,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.width(48.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (isAlphabeticallyFirstInCharGroup) {
                Text(
                    text = item.fullName.first().toString(),
                    style = charStyle,
                )
            }
        }

        Surface(
            shape = CircleShape, color = MaterialTheme.colorScheme.secondary
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(iconSize)
            )
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = item.fullName,
            style = nameTextStyle,
        )
    }
}

data class Contact(val fullName: String, val image: Int)

private val alphabetItemSize = 24.dp

private fun List<Contact>.getFirstUniqueSeenCharIndex(): Map<Char, Int> {
    val firstLetterIndexes = mutableMapOf<Char, Int>()
    this.map { it.fullName.lowercase().first() }.forEachIndexed { index, char ->
        if (!firstLetterIndexes.contains(char)) {
            firstLetterIndexes[char] = index
        }
    }
    return firstLetterIndexes
}

private fun Float.getIndexOfCharBasedOnYPosition(
    alphabetHeightInPixels: Float,
): Char {
    val alphabetCharList = "abcdefghijklmnopqrstuvwxyz".map { it }

    var index = ((this) / alphabetHeightInPixels).toInt()
    index = when {
        index > 25 -> 25
        index < 0 -> 0
        else -> index
    }
    return alphabetCharList[index]
}

