package io.github.sgpublic.uniktx.common.core.util

import android.content.Context
import androidx.annotation.StringRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Madray Haven
 * @Date 2023/7/27 10:41
 */

suspend fun Context.Toast(content: String) {
    withContext(Dispatchers.Main) {
        android.widget.Toast.makeText(
            this@Toast, content,
            if (content.length > 10) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}

suspend fun Context.Toast(@StringRes content: Int, message: String? = null, code: Int? = null) {
    Toast(getString(content)
            + ("".takeIf { message == null } ?: "，$message")
            + ("".takeIf { code == null } ?: " (${code})"))
}