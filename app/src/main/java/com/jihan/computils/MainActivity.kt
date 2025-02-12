package com.jihan.computils

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jihan.composeutils.CenterBox
import com.jihan.composeutils.CxMultiStepLoader
import com.jihan.composeutils.CxWheelTimePicker
import com.jihan.computils.ui.demo.ExpandableCardDemo
import com.jihan.computils.ui.demo.MultiStepLoaderDemo
import com.jihan.computils.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ComposeUtils()

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeUtils() {
    Scaffold {


        CenterBox(Modifier
            .padding(it)) {

            MultiStepLoaderDemo()
        }
    }
}



