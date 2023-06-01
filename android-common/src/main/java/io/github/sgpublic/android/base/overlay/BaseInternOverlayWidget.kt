package io.github.sgpublic.android.base.overlay

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.kotlin.util.Loggable
import io.github.sgpublic.kotlin.util.log
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.abs


/**
 * @author Madray Haven
 * @Date 2023/5/30 15:41
 */
abstract class BaseInternOverlayWidget<VB: ViewBinding> protected constructor(
    private val context: AppCompatActivity
): LifecycleOwner, Loggable {
    private val parent: ViewGroup by lazy {
        context.window.decorView.findViewById(android.R.id.content)
    }

    private lateinit var _vb: VB
    protected val ViewBinding: VB get() = _vb

    abstract fun onCreateViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB


    @CallSuper
    protected open fun beforeCreate() { }


    private val created = AtomicBoolean(false)
    fun create() {
        synchronized(created) {
            if (created.get()) return
            _vb = onCreateViewBinding(LayoutInflater.from(context), parent)
            beforeCreate()
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
            onSetupView()
            log.debug("onCreate()")
            onCreate()
            parent.addView(ViewBinding.root)
            created.set(true)
        }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        ViewBinding.root.visibility = View.GONE
    }

    @CallSuper
    protected open fun onCreate() {}


    private var hadHide = false
    open fun show() {
        if (ViewBinding.root.visibility == View.VISIBLE) {
            return
        }
        ViewBinding.root.visibility = View.VISIBLE
        lifecycleRegistry.currentState = if (hadHide) Lifecycle.State.RESUMED else Lifecycle.State.STARTED
        log.debug("onShow()")
        onShow()
    }

    @CallSuper
    protected open fun onShow() {}

    open fun hide() {
        if (ViewBinding.root.visibility == View.GONE) {
            return
        }
        ViewBinding.root.visibility = View.GONE
        hadHide = true
        log.debug("onHide()")
        onHide()
    }

    @CallSuper
    protected open fun onHide() {}

    open fun destroy() {
        synchronized(created) {
            if (!created.get()) return
            parent.removeView(ViewBinding.root)
            created.set(false)
        }
        log.debug("onDestroy()")
        onDestroy()
    }

    @CallSuper
    protected open fun onDestroy() {}

    @CallSuper
    protected open fun onSetupView() {
        getDragView(ViewBinding)?.setOnTouchListener(object : OnTouchListener {
            private var lastX = 0f
            private var lastY = 0f
            private var downX = 0f
            private var downY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        run {
                            downX = event.rawX
                            lastX = downX
                        }
                        run {
                            downY = event.rawY
                            lastY = downY
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        ViewBinding.root.x = ViewBinding.root.x + event.rawX - lastX
                        ViewBinding.root.y = ViewBinding.root.y + event.rawY - lastY
                        lastX = event.rawX
                        lastY = event.rawY
                        onOverlayDrag(lastX, lastY)
                    }

                    MotionEvent.ACTION_UP -> if (abs(event.rawX - downX) < 10 &&
                        abs(event.rawY - downY) < 10
                    ) {
                        v.performClick()
                    }
                }
                return true
            }
        })
    }

    /**
     * 返回 null 则表示不可拖动
     * @return 拖动时间附加目标 View
     */
    protected open fun getDragView(ViewBinding: VB): View? = null

    protected open fun onOverlayDrag(x: Float, y: Float) {}

    private val lifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}