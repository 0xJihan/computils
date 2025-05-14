package com.jihan.computils.ui.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.composeutils.ui.CxRollerEffectText

@Composable
fun RollerEffectTextDemo() {
    var count by remember {
        mutableIntStateOf(0)
    }

    CxRollerEffectText(
        count = count,
        style = TextStyle(
            color = Color(0xFF3E3E3E),
            fontSize = 32.sp
        ),
        modifier = Modifier.clickable{
            count-=1
        }
    )


    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4579FF)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            count++
        }
    ) {
        Text(
            text = "Increment",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}