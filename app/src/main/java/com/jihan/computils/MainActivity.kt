package com.jihan.computils

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.jihan.composeutils.gap

import com.jihan.computils.ui.demo.RollerEffectTextDemo
import com.jihan.computils.ui.demo.ShakeExample
import com.jihan.computils.ui.demo.SimpleBottomNavDemo
import com.jihan.computils.ui.demo.SimpleBottomNavWithDotDemo
import com.jihan.computils.ui.demo.SimpleBottomNavWithLineDemo
import com.jihan.computils.ui.demo.TypewriterTextDemo
import com.jihan.computils.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
               CenterBox {

                   ShakeExample()

               }
            }
        }
    }
}


@Composable
fun CenterBox(
    modifier: Modifier = Modifier.fillMaxSize(), content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

