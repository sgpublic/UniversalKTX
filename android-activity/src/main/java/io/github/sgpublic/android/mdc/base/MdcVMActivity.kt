package io.github.sgpublic.android.mdc.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class MdcVMActivity<VB: ViewBinding, VM: ViewModel>: MdcActivity<VB>() {
    @Suppress("PropertyName")
    protected abstract val ViewModel: VM

    @CallSuper
    override fun beforeCreate() {
        super.beforeCreate()
        onViewModelSetup()
    }

    protected open fun onViewModelSetup() { }
}