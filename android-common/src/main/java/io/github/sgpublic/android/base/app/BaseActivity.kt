package io.github.sgpublic.android.base.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.yanzhenjie.sofia.Sofia
import io.github.sgpublic.android.Application
import io.github.sgpublic.android.common.R
import io.github.sgpublic.android.core.util.Toast
import io.github.sgpublic.android.core.util.finishAll
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

        Sofia.with(this)
            .statusBarBackgroundAlpha(0)
            .navigationBarBackgroundAlpha(0)
            .invasionNavigationBar()
            .invasionStatusBar()
            .statusBarDarkFont()
    }

    protected open fun onViewSetup() { }

    protected inline fun <reified VB: ViewBinding> viewBinding(): Lazy<VB> = lazy {
        VB::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }
}