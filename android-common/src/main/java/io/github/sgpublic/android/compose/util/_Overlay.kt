package io.github.sgpublic.android.compose.util

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

/**
 * @author Madray Haven
 * @Date 2023/6/27 20:26
 */

fun OverlayComposeView(context: Context): ComposeView {
    val composeView = ComposeView(context)
    val lifecycle = object : LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner {
        private val lifecycleRegex = LifecycleRegistry(this)
        override val lifecycle: Lifecycle
            get() = lifecycleRegex
        private val savedStateRegistryController =
            SavedStateRegistryController.create(this)
        override val savedStateRegistry: SavedStateRegistry
            get() = savedStateRegistryController.savedStateRegistry
        private val mViewModelStore = ViewModelStore()
        override val viewModelStore: ViewModelStore
            get() = mViewModelStore

        fun performRestore(savedState: Bundle?) {
            savedStateRegistryController.performRestore(savedState)
        }
        fun handleLifecycleEvent(event: Lifecycle.Event) {
            lifecycleRegex.handleLifecycleEvent(event)
        }
    }
    lifecycle.performRestore(null)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    composeView.setViewTreeLifecycleOwner(lifecycle)
    composeView.setViewTreeViewModelStoreOwner(lifecycle)
    composeView.setViewTreeSavedStateRegistryOwner(lifecycle)

    return composeView
}