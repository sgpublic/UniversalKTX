package io.github.sgpublic.android.base.app

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelActivity<VB: ViewBinding, VM: ViewModel>: BaseActivity<VB>() {
    @Suppress("PropertyName")
    protected abstract val ViewModel: VM

    @CallSuper
    override fun beforeCreate() {
        super.beforeCreate()
        onViewModelSetup()
    }

    protected open fun onViewModelSetup() { }
}