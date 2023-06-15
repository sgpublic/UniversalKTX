package io.github.sgpublic.android.core.util

import androidx.activity.OnBackPressedDispatcherOwner
import com.google.android.material.appbar.MaterialToolbar

/**
 * @author Madray Haven
 * @Date 2023/5/10 11:10
 */

fun OnBackPressedDispatcherOwner.setupToolbar(toolbar: MaterialToolbar, onBack: () -> Unit = {
    onBackPressedDispatcher.onBackPressed()
}) {
    toolbar.setNavigationOnClickListener {
        onBack.invoke()
    }
}