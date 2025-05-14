package com.jihan.computils.ui.demo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.TimeFormat
import com.jihan.composeutils.ui.WheelPickerDefaults
import com.jihan.composeutils.ui.CxWheelTimePicker
import com.jihan.composeutils.ui.timeToString

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelTimePickerDemo() {
    var showSheet by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showSheet) {
        CxWheelTimePicker(modifier = Modifier.fillMaxWidth(),
            titleStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
            ),
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
                color = Color(0xFF007AFF),
            ),
            textColor = Color(0xff007AFF),
            timeFormat = TimeFormat.AM_PM,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            rowCount = 5,
            size = DpSize(128.dp, 160.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            onDismiss = {
                showSheet = false
            },
            onDoneClick = {
                selectedDate = timeToString(it, "hh:mm a")
                showSheet = false
            })
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                showSheet = true
            }) {
                Text(text = "Show BottomSheet")
            }
            Text(
                text = selectedDate,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}