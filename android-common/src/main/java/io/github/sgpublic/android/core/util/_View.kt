package io.github.sgpublic.android.core.util

import android.animation.Animator
import android.view.View

fun View.animate(isVisible: Boolean, duration: Long, onFinished: (() -> Unit)? = null) {
    post {
        animate().apply {
            if (isVisible) {
                alphaBy(0f).alpha(1f)
            } else {
                alphaBy(1f).alpha(0f)
            }
        }.setDuration(duration).also {
            if (onFinished != null) {
                it.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {}
                    override fun onAnimationCancel(p0: Animator) {}
                    override fun onAnimationRepeat(p0: Animator) {}
                    override fun onAnimationEnd(p0: Animator) {
                        onFinished.invoke()
                    }
                })
            }
        }.start()
    }
}