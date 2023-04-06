package io.github.sgpublic.android.base.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.yanzhenjie.sofia.Sofia
import io.github.sgpublic.android.Application
import io.github.sgpublic.android.R
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

    protected open fun isActivityAtBottom(): Boolean = false

    private var last: Long = -1
    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BaseFragment<*> && it.onBackPressed()) {
                return
            }
        }
        if (!isActivityAtBottom()){
            @Suppress("DEPRECATION")
            super.onBackPressed()
            return
        }
        val now = System.currentTimeMillis()
        if (last < 0) {
            Toast(R.string.text_back_exit)
            last = now
        } else {
            if (now - last < 2000) {
                Application.finishAll()
            } else {
                last = -1
                Toast(R.string.text_back_exit_notice)
            }
        }
    }

    protected inline fun <reified VB: ViewBinding> viewBinding(): Lazy<VB> = lazy {
        VB::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }
}