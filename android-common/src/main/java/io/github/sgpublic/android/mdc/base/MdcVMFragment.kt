package io.github.sgpublic.android.mdc.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class MdcVMFragment<VB: ViewBinding, VM: ViewModel>(context: AppCompatActivity)
    : MdcFragment<VB>(context) {
    @Suppress("PropertyName")
    protected abstract val ViewModel: VM

    override fun beforeFragmentCreated() {
        onViewModelSetup()
    }

    protected open fun onViewModelSetup() { }
}