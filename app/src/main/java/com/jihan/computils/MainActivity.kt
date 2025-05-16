package com.jihan.computils

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jihan.composeutils.core.CenterBox
import com.jihan.composeutils.core.Gap
import com.jihan.composeutils.ui.CxButton
import com.jihan.composeutils.ui.CxSnackBar
import com.jihan.composeutils.ui.CxSnackBarPosition
import com.jihan.composeutils.ui.rememberCxSnackBarState
import com.jihan.computils.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AppTheme {
                DemoScreen()


            }
        }
    }


    @Composable
    fun DemoScreen() {



        val successSnack = rememberCxSnackBarState()
        val warningSnack = rememberCxSnackBarState()
        val errorSnack = rememberCxSnackBarState()
        val customSnack = rememberCxSnackBarState()




        CenterBox {

            CxButton("Success", Modifier.fillMaxWidth()) { successSnack.addMessage("Success SnackBar") }
            Gap(20)
            CxButton("Error", Modifier.fillMaxWidth()) { errorSnack.addMessage("Error SnackBar") }
            Gap(20)
            CxButton("Warning", Modifier.fillMaxWidth()) { warningSnack.addMessage("Warning SnackBar") }
        }
        CxSnackBar.Success(
            state = successSnack,
            position = CxSnackBarPosition.Top
        )
        CxSnackBar.Error(
            state = errorSnack,
            position = CxSnackBarPosition.Bottom
        )
        CxSnackBar.Warning(
            state = warningSnack,
            position = CxSnackBarPosition.Float
        )

    }

}



