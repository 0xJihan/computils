package com.jihan.composeutils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

sealed class FileType(val mimeType: String) {
    // Images
    data object Image : FileType("image/*")
    data object PNG : FileType("image/png")
    data object JPEG : FileType("image/jpeg")
    data object SVG : FileType("image/svg+xml")
    data object GIF : FileType("image/gif")

    // Documents
    data object PDF : FileType("application/pdf")
    data object DOC : FileType("application/msword")
    data object DOCX :
        FileType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")

    data object XLS : FileType("application/vnd.ms-excel")
    data object XLSX : FileType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    data object TXT : FileType("text/plain")

    // Audio
    data object Audio : FileType("audio/*")
    data object MP3 : FileType("audio/mpeg")
    data object WAV : FileType("audio/wav")

    // Video
    data object Video : FileType("video/*")
    data object MP4 : FileType("video/mp4")
    data object MKV : FileType("video/x-matroska")

    // Archives
    data object ZIP : FileType("application/zip")
    data object RAR : FileType("application/x-rar-compressed")

    // Any
    data object Any : FileType("*/*")

    // Custom
    class Custom(mimeType: String) : FileType(mimeType)
}


sealed class PickerType {
    data class Single(val fileType: FileType = FileType.Any) : PickerType()
    data class Multiple(val fileType: FileType = FileType.Any) : PickerType()
}

class CxFilePicker(
    private val type: PickerType = PickerType.Single(),
    private val onPicked: (List<Uri>) -> Unit = {}
) {
    private val _pickedFilesFlow = MutableStateFlow<List<Uri>>(emptyList())
    val pickedFilesFlow: StateFlow<List<Uri>> = _pickedFilesFlow

    private var singleLauncher: (() -> Unit)? = null
    private var multipleLauncher: (() -> Unit)? = null
    private var awaitContinuation: ((List<Uri>) -> Unit)? = null

    fun setSingleLauncher(launch: () -> Unit) {
        singleLauncher = launch
    }

    fun setMultipleLauncher(launch: () -> Unit) {
        multipleLauncher = launch
    }

    fun pick() {
        when (type) {
            is PickerType.Single -> singleLauncher?.invoke()
            is PickerType.Multiple -> multipleLauncher?.invoke()
        }
    }

    suspend fun pickAwait(): List<Uri> = suspendCancellableCoroutine { continuation ->
        awaitContinuation = { uris ->
            awaitContinuation = null
            continuation.resume(uris)
        }
        pick()

        continuation.invokeOnCancellation {
            awaitContinuation = null
        }
    }

    fun handleResult(uris: List<Uri>) {
        _pickedFilesFlow.value = uris
        awaitContinuation?.invoke(uris)
        onPicked(uris)
    }
}

@Composable
fun rememberCxFilePicker(
    type: PickerType = PickerType.Single(),
    onPicked: (List<Uri>) -> Unit = {}
): CxFilePicker {
    val scope = rememberCoroutineScope()
    val filePicker = remember { CxFilePicker(type, onPicked) }

    val singleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        scope.launch {
            filePicker.handleResult(listOfNotNull(uri))
        }
    }

    val multipleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        scope.launch {
            filePicker.handleResult(uris)
        }
    }

    remember(singleLauncher, multipleLauncher) {
        val mimeType = when (type) {
            is PickerType.Single -> type.fileType.mimeType
            is PickerType.Multiple -> type.fileType.mimeType
        }
        filePicker.setSingleLauncher { singleLauncher.launch(mimeType) }
        filePicker.setMultipleLauncher { multipleLauncher.launch(mimeType) }
        filePicker
    }

    return filePicker
}