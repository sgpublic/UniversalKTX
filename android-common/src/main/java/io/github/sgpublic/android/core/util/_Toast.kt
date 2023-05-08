package io.github.sgpublic.android.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Madray Haven
 * @Date 2023/5/8 11:28
 */


fun ComponentActivity.Toast(content: String) = lifecycleScope.launch {
    Toast(this@Toast, content)
}
fun Fragment.Toast(content: String) = lifecycleScope.launch {
    Toast(requireContext(), content)
}
suspend fun Toast(context: Context, content: String) = withContext(Dispatchers.Main) {
    android.widget.Toast.makeText(
        context, content,
        if (content.length > 10) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT
    ).show()
}


fun ComponentActivity.Toast(@StringRes content: Int) = lifecycleScope.launch {
    Toast(this@Toast, content)
}
fun Fragment.Toast(@StringRes content: Int) = lifecycleScope.launch {
    Toast(requireContext(), content)
}
suspend fun Toast(context: Context, @StringRes content: Int) {
    Toast(context, context.getString(content))
}


fun ComponentActivity.Toast(@StringRes content: Int, code: Int) = lifecycleScope.launch {
    Toast(this@Toast, content, code)
}
fun Fragment.Toast(@StringRes content: Int, code: Int) = lifecycleScope.launch {
    Toast(requireContext(), content, code)
}
suspend fun Toast(context: Context, @StringRes content: Int, code: Int) {
    Toast(context, "${context.getString(content)} ($code)")
}


fun ComponentActivity.Toast(@StringRes content: Int, message: String?, code: Int) = lifecycleScope.launch {
    Toast(this@Toast, content, message, code)
}
fun Fragment.Toast(@StringRes content: Int, message: String?, code: Int) = lifecycleScope.launch {
    Toast(requireContext(), content, message, code)
}
suspend fun Toast(context: Context, @StringRes content: Int, message: String?, code: Int) {
    Toast(context, context.getString(content) +
            "，".takeIf { message != null } +
            "${message ?: ""} ($code)")
}