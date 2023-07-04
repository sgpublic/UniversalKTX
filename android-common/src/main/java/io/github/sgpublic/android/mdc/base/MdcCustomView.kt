package io.github.sgpublic.android.mdc.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.StyleableRes
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.android.core.util.ContextResource

/**
 * @author Madray Haven
 * @Date 2023/4/6 11:24
 */
abstract class MdcCustomView<VB : ViewBinding> @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), ContextResource {
    private var _vb: VB? = null
    protected val ViewBinding: VB get() = _vb!!

    init {
        _vb = onCreateViewBinding(LayoutInflater.from(context))
        onInit(context, _vb!!)
    }

    abstract fun onCreateViewBinding(layoutInflater: LayoutInflater): VB

    /**
     * 初始化自定义 View
     * @param context context
     */
    protected abstract fun onInit(context: Context, ViewBinding: VB)

    /**
     * 读取样式参数，自动释放，仅允许在 onInit 中调用
     * @param styledRes R.styleable.xxx
     * @param block 在 block 中使用 TypedArray
     * @see android.content.res.TypedArray
     */
    protected fun useStyleRes(@StyleableRes styledRes: IntArray, block: TypedArray.() -> Unit) {
        context.obtainStyledAttributes(attrs, styledRes).use(block)
    }

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

/**
 * 读取样式参数，自动释放，仅允许在 onInit 中调用
 * @param attrs 参数集
 * @param styledRes R.styleable.xxx
 * @param block 在 block 中使用 TypedArray
 * @see android.content.res.TypedArray
 */
fun ContextResource.useStyleRes(
    attrs: AttributeSet?,
    @StyleableRes styledRes: IntArray,
    block: TypedArray.() -> Unit
) {
    getContext().obtainStyledAttributes(attrs, styledRes).use(block)
}