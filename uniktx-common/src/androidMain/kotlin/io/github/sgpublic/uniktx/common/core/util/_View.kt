package io.github.sgpublic.uniktx.common.core.util

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

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

interface LayoutInflaterProvider {
    fun getLayoutInflater(): LayoutInflater
}

inline fun <reified VB: ViewBinding> LayoutInflaterProvider.viewBinding(): Lazy<VB> = lazy {
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, getLayoutInflater()) as VB
}

fun RecyclerView.isItemVisible(
    position: Int,
    completelyVisible: Boolean = true,
    acceptEndPointInclusion: Boolean = true,
): Boolean {
    val layoutManager = layoutManager ?: return false
    return layoutManager.isViewPartiallyVisible(
        layoutManager.getChildAt(position) ?: return false,
        completelyVisible, acceptEndPointInclusion
    )
}

val Boolean.visibility: Int get() = if (this) View.VISIBLE else View.GONE