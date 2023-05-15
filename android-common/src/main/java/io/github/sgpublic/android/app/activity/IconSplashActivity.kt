package io.github.sgpublic.android.app.activity

import android.annotation.SuppressLint
import io.github.sgpublic.android.base.app.BaseActivity
import io.github.sgpublic.android.common.databinding.ActivitySplashBinding
import io.github.sgpublic.android.core.util.viewBinding


/**
 * @author Madray Haven
 * @Date 2023/4/12 8:28
 */
@SuppressLint("CustomSplashScreen")
abstract class IconSplashActivity: BaseActivity<ActivitySplashBinding>() {
    final override fun onActivityCreated(hasSavedInstanceState: Boolean) {
        if (!isTaskRoot) {
            finish()
            return
        }
        doSplash()
    }

    abstract fun doSplash()

    final override val ViewBinding: ActivitySplashBinding by viewBinding()
}