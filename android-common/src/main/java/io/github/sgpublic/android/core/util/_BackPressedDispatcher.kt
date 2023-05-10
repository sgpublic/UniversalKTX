package io.github.sgpublic.android.core.util

import androidx.activity.OnBackPressedDispatcher
import com.google.android.material.appbar.MaterialToolbar

/**
 * @author Madray Haven
 * @Date 2023/5/10 11:10
 */
interface BackPressedDispatcherProvider {
    fun getOnBackPressedDispatcher(): OnBackPressedDispatcher
}

fun BackPressedDispatcherProvider.setupToolbar(toolbar: MaterialToolbar) {
    toolbar.setNavigationOnClickListener {
        getOnBackPressedDispatcher().onBackPressed()
    }
}