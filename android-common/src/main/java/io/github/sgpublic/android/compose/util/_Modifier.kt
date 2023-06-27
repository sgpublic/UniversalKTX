package io.github.sgpublic.android.compose.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * @author Madray Haven
 * @Date 2023/6/27 20:15
 */

fun Modifier.borderlessClickable(block: () -> Unit) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = false),
        onClick = block
    )
}