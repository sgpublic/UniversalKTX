package io.github.sgpublic.android.mdc.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class MdcActivity<VB : ViewBinding>: BaseCompatActivity() {
    private var _binding: VB? = null
    @Suppress("PropertyName")
    protected abstract val ViewBinding: VB

    private var rootViewBottom: Int = 0

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContentView()
        onViewSetup()
        onActivityCreated(savedInstanceState)
    }

    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    private fun setupContentView() {
        setContentView(ViewBinding.root)
        if (applySofia) {
            applySofia()
        }
    }

    protected open val applySofia: Boolean = true

    protected open fun onViewSetup() { }
}