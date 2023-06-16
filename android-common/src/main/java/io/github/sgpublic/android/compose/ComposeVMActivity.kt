package io.github.sgpublic.android.compose

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * @author Madray Haven
 * @Date 2023/6/15 9:19
 */
abstract class ComposeVMActivity<VM: ViewModel>: ComposeCompatActivity() {
    @Suppress("PropertyName")
    protected abstract val ViewModel: VM

    @CallSuper
    override fun beforeCreate() {
        super.beforeCreate()
        onViewModelSetup()
    }

    protected open fun onViewModelSetup() { }
}