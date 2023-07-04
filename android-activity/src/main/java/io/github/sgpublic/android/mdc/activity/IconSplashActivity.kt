package io.github.sgpublic.android.mdc.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import io.github.sgpublic.android.mdc.base.MdcActivity
import io.github.sgpublic.android.common.databinding.ActivitySplashBinding
import io.github.sgpublic.android.core.util.viewBinding


/**
 * @author Madray Haven
 * @Date 2023/4/12 8:28
 */
@SuppressLint("CustomSplashScreen")
abstract class IconSplashActivity: MdcActivity<ActivitySplashBinding>() {
    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (!isTaskRoot) {
            finish()
            return
        }
        ViewBinding.splashIcon.setImageDrawable(
            ResourcesCompat.getDrawable(resources, SplashIcon, theme)
        )
        doSplash()
    }

    @get:DrawableRes
    abstract val SplashIcon: Int

    abstract fun doSplash()

    final override val ViewBinding: ActivitySplashBinding by viewBinding()
}