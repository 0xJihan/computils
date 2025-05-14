package com.jihan.computils

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.jihan.composeutils.core.CenterBox
import com.jihan.composeutils.ui.CxButton
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


@Composable
fun ComposeUtils() {
    CenterBox {

        CxButton {
        }


    }
}

}



