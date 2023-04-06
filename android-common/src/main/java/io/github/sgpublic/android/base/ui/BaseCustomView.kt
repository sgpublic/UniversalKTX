package io.github.sgpublic.android.base.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.StyleableRes
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.android.core.util.ContextResource
import java.util.concurrent.atomic.AtomicReference

/**
 * @author Madray Haven
 * @Date 2023/4/6 11:24
 */

abstract class BaseCustomView<VB : ViewBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), ContextResource {
    @StyleableRes
    protected val StyleableAttrs: IntArray? = null

    init {
        val a = AtomicReference<TypedArray>()
        try {
            StyleableAttrs?.let {
                a.set(getContext().obtainStyledAttributes(attrs, it))
            }
            onInit(context, a.get())
        } finally {
            a.get()?.recycle()
        }
    }

    protected abstract val ViewBinding: VB

    /**
     * 初始化自定义 View
     * @param context context
     * @param attrs 参数集，会自动释放。当 BaseCustomView#getStyleableAttr() 方法返回 null 时，此参数传入 null
     */
    protected abstract fun onInit(context: Context?, attrs: TypedArray?)

    @CallSuper
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        ViewBinding.root.let {
            measureChild(it, widthMeasureSpec, heightMeasureSpec)
            setMeasuredDimension(it.measuredWidth, it.measuredHeight)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        ViewBinding.root.let {
            it.layout(0, 0, it.measuredWidth, it.measuredHeight)
        }
    }
}
