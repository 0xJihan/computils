package com.jihan.computils.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.CxPicker
import com.jihan.composeutils.ui.rememberPickerState

@Composable
fun NumberPickerDemo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        val values = remember { (1..9999).map { it.toString() } }
        val valuesPickerState = rememberPickerState()

        CxPicker(
            state = valuesPickerState,
            items = values,
            visibleItemsCount = 5,
            modifier = Modifier.fillMaxWidth(0.5f),
            textModifier = Modifier.padding(8.dp),
            textStyle = TextStyle(fontSize = 32.sp),
            dividerColor = Color(0xFFE8E8E8)
        )

        Text(
            text = "Result: ${valuesPickerState.selectedItem}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.5f)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 8.dp))
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )
    }
}