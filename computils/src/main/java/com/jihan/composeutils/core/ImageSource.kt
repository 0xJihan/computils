package com.jihan.composeutils.core

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ImageSource {
    data class Res(val resId: Int) : ImageSource()
    data class Vector(val vector: ImageVector) : ImageSource()
}