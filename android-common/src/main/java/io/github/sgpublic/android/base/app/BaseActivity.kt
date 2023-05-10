package io.github.sgpublic.android.base.app

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.yanzhenjie.sofia.Sofia
import java.util.*

abstract class BaseActivity<VB : ViewBinding>: BaseCompatActivity() {
    private var _binding: VB? = null
    @Suppress("PropertyName")
    protected abstract val ViewBinding: VB

    private var rootViewBottom: Int = 0

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContentView()
        onViewSetup()
        onActivityCreated(savedInstanceState != null)
    }

    protected abstract fun onActivityCreated(hasSavedInstanceState: Boolean)

    private fun setupContentView() {
        setContentView(ViewBinding.root)
        if (applySofia) {
            applySofia()
        }
    }

    protected open val applySofia: Boolean = true

    protected open fun onViewSetup() { }
}