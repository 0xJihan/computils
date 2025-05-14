package com.jihan.composeutils.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jihan.composeutils.core.Gap

@Composable
fun CxLoadingDialog(loading: Boolean=true,text:String="Loading...") {
    if (loading) {

        Dialog(onDismissRequest = {}) {
            Card(
                elevation = CardDefaults.cardElevation(
                    8.dp
                )
            ) {
                Column(
                    Modifier
                        .sizeIn(
                            minWidth = 120.dp,
                            minHeight = 120.dp
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Gap(10)
                    Text(text)
                }
            }
        }

    }

}