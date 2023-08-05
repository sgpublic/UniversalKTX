package io.github.sgpublic.android.compose.util

import android.content.Context
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
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
    val lifecycle = object : SavedStateRegistryOwner, ViewModelStoreOwner {
        private val lifecycleRegex = LifecycleRegistry(this)
        override fun getLifecycle(): Lifecycle {
            return lifecycleRegex
        }
        private val savedStateRegistryController =
            SavedStateRegistryController.create(this)

        override val savedStateRegistry: SavedStateRegistry
            get() = savedStateRegistryController.savedStateRegistry
        private val mViewModelStore = ViewModelStore()
        override fun getViewModelStore(): ViewModelStore {
            return mViewModelStore
        }

        fun performRestore(savedState: Bundle?) {
            savedStateRegistryController.performRestore(savedState)
        }
        fun handleLifecycleEvent(event: Lifecycle.Event) {
            lifecycleRegex.handleLifecycleEvent(event)
        }
    }
    lifecycle.performRestore(null)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    ViewTreeLifecycleOwner.set(composeView, lifecycle)
    ViewTreeViewModelStoreOwner.set(composeView, lifecycle)
    composeView.setViewTreeSavedStateRegistryOwner(lifecycle)

    return composeView
}