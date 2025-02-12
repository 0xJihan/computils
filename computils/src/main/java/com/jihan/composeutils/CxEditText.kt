package com.jihan.composeutils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CxEditText(
    value: String,
    label: String="",
    leadingIcon: Int? = null,
    padding: Dp = 4.dp,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10))
        .padding(padding),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    colors :TextFieldColors = TextFieldDefaults.colors(),
    shape: Shape = RectangleShape,
    onValueChange: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, ) },
        singleLine = true,
        colors = colors,
        shape = shape,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        trailingIcon = {

            Row {
                AnimatedVisibility(value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(R.drawable.x),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                }

                if (keyboardType in listOf(KeyboardType.Password, KeyboardType.NumberPassword))
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(if (isPasswordVisible) R.drawable.eye_closed else R.drawable.eye),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

            }
        },
        visualTransformation = if (keyboardType in listOf(
                KeyboardType.Password, KeyboardType.NumberPassword
            ) && !isPasswordVisible
        ) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = keyboardType == KeyboardType.Text,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}
