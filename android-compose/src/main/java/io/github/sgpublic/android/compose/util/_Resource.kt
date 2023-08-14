package io.github.sgpublic.android.compose.util

import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap

/**
 * @author Madray Haven
 * @Date 2023/8/14 11:06
 */


@Composable
@ReadOnlyComposable
fun bitmapResource(@DrawableRes id: Int): ImageBitmap {
    val context = LocalContext.current
    val drawable = context.getDrawable(id)
        ?: throw IllegalArgumentException("Resource $id not found!")
    return drawable.toBitmap().asImageBitmap()
}