package com.jihan.computils.ui.demo

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jihan.composeutils.ui.CxButton
import com.jihan.composeutils.core.FileType
import com.jihan.composeutils.core.PickerType
import com.jihan.composeutils.core.rememberCxFilePicker
import kotlinx.coroutines.launch

@Composable
fun CxFilePickerDemo(modifier: Modifier = Modifier) {


    val selectedImage = remember { mutableStateOf<Uri?>(null) }

    // Picking single image
    val imagePicker = rememberCxFilePicker(PickerType.Single(FileType.Image)){uris ->
        //? since we are picking single image, we can safely assume that the first uri in the list is the selected image
        if (uris.isNotEmpty()){
            selectedImage.value = uris[0]
        }

    }

    // Picking multiple images
    val videPicker = rememberCxFilePicker(PickerType.Multiple(FileType.Video)){uris: List<Uri> ->
        //? handle multiple images here
    }

    // Collecting picked videos through flow
    val pickedVideos = videPicker.pickedFilesFlow.collectAsStateWithLifecycle()


    CxButton("Pick Image") {
        imagePicker.pick()
    }

    val scope = rememberCoroutineScope()
    CxButton("Pick Video") {
      scope.launch {
          val list = videPicker.pickAwait()
          // using coroutine scope to call pickAwait() function
      }
    }



}