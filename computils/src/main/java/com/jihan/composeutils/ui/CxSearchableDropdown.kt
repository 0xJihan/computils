package com.jihan.composeutils.ui


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.core.ifEmpty
import kotlin.collections.iterator


//! ======================================================
//! ======================================================

private val DropdownMenuVerticalPadding = 8.dp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)
@Composable
fun <T> CxSearchableDropdown(
    items: List<T>,
    selectedText:(T)->String,
    dropdownItem: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    fieldLabel: String = "",
    enable: Boolean = true,
    readOnly: Boolean = true,
    placeholder: @Composable (() -> Unit) = {
        Text(
            text = "Select Option",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    },

    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        errorContainerColor = MaterialTheme.colorScheme.background,
    ),
    isError: Boolean = false,
    showDefaultSelectedItem: Boolean = true,
    defaultItemIndex: Int = 0,
    defaultItem: (T) -> Unit = {},
    reset: Boolean = false,
    parentTextFieldShape: CornerBasedShape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    onDropDownItemSelected: (T) -> Unit = {},
) {
    var selectedOptionText by remember { mutableStateOf("") }
    var searchedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var filteredItems = mutableListOf<T>()
    val keyboardController = LocalSoftwareKeyboardController.current
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current


    if (showDefaultSelectedItem) {
        selectedOptionText = selectedOptionText.ifEmpty { selectedText(items[defaultItemIndex]) }

        defaultItem(
            items[defaultItemIndex],
        )
    }

    if (reset) {
        selectedOptionText = ""
    }

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != items.indices.toSet()) {
            // if we don't have all heights calculated yet, return default value

            val screenHeight = configuration.screenHeightDp.dp
            return@remember if (screenHeight < baseHeight) {
                screenHeight
            } else baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        // top+bottom system padding
        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((_, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        // all items fit into base height
        baseHeight
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = modifier.semantics { testTag = fieldLabel; testTagsAsResourceId = true },
            colors = colors,
            value = selectedOptionText,
            readOnly = readOnly,
            enabled = enable,
            onValueChange = { selectedOptionText = it },
            placeholder = placeholder,
            textStyle = textStyle,
            label = { Text(text = fieldLabel) },
            trailingIcon = {
                IconToggleButton(
                    modifier = Modifier.semantics {
                            testTag = "dropDownIcon"; testTagsAsResourceId = true
                        },
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    },
                ) {
                    if (expanded) {
                        Icon(
                            imageVector = openedIcon,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            imageVector = closedIcon,
                            contentDescription = null,
                        )
                    }
                }
            },
            shape = parentTextFieldShape,
            isError = isError,
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        keyboardController?.show()
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded = !expanded
                            }
                        }
                    }
                },
        )
        if (expanded) {
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .requiredSizeIn(maxHeight = maxHeight),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchedOption,
                        onValueChange = { selectedSport ->
                            searchedOption = selectedSport
                            filteredItems = items.filter {
                                it.toString().contains(
                                    searchedOption,
                                    ignoreCase = true,
                                )
                            }.toMutableList()
                        },
                        textStyle = textStyle,
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                    )

                    val itemsList = if (filteredItems.isEmpty()) {
                        items
                    } else {
                        filteredItems
                    }

                    //!===========================================
                    itemsList.forEach { selectedItem ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                keyboardController?.hide()
                                selectedOptionText =selectedText(selectedItem)
                                onDropDownItemSelected(selectedItem)
                                searchedOption = ""
                                expanded = false
                            },
                            text = {
                                dropdownItem(selectedItem)
                            },
                        )
                    }
                }
            }
        }
    }
}
