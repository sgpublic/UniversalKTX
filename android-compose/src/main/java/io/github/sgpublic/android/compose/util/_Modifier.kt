package io.github.sgpublic.android.compose.util

import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * @author Madray Haven
 * @Date 2023/6/27 20:15
 */
@Composable
fun rememberBorderlessRipple(
    color: Color = Color.Unspecified,
    radius: Dp = Dp.Unspecified,
) = rememberRipple(
    bounded = false,
    color = color,
    radius = radius
)