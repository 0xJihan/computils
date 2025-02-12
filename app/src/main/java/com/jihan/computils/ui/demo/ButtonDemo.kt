package com.jihan.computils.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jihan.composeutils.CxButton
import com.jihan.composeutils.CxElevatedButton
import com.jihan.composeutils.CxOutlinedButton
import com.jihan.composeutils.CxTextButton
import com.jihan.composeutils.text
import com.jihan.composeutils.toast

@Composable
fun ButtonDemo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    CxButton(loading = loading) {  }
    CxElevatedButton("Elevated Button") { loading=true}
    CxOutlinedButton("Outlined Button") { loading=false}
    CxTextButton("Text Button", loading = loading,
        onLoadingContent = {
            "Loading...".text.make()
        }) {
        "Button Clicked".toast(context)
    }
}