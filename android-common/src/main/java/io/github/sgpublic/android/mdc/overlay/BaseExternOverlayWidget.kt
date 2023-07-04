package io.github.sgpublic.android.mdc.overlay

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.kotlin.util.Loggable

/**
 * @author Madray Haven
 * @Date 2023/6/6 17:09
 */
abstract class BaseExternOverlayWidget<VB: ViewBinding>
@RequiresPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) constructor(
    protected val context: Activity,
): LifecycleOwner, Loggable {
//    protected abstract val ViewBinding: VB
//    protected val LayoutParams = WindowManager.LayoutParams().also {
//        it.width = WindowManager.LayoutParams.WRAP_CONTENT
//        it.height = WindowManager.LayoutParams.WRAP_CONTENT
//        it.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        it.format = PixelFormat.TRANSLUCENT
//        it.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//    }
//
//    protected open fun beforeCreate() { }
//
//    private var created: Boolean = false
//
//    protected val WindowManagerService: WindowManager = context.getSystemService(WindowManager::class.java)
//
//    open fun create() {
//        if (created) return
//        this.beforeCreate()
//        this.onSetupLayoutParams(LayoutParams)
//        this.onSetupView()
//        lifecycleOwner.currentState = Lifecycle.State.CREATED
//        created = true
//        log.debug("onCreate()")
//        this.onCreate()
//        WindowManagerService.addView(ViewBinding.root, LayoutParams)
//        ViewBinding.root.visibility = View.GONE
//    }
//    protected open fun onCreate() { }
//
//    private var hadHide = false
//    fun show() {
//        if (ViewBinding.root.visibility == View.VISIBLE) {
//            return
//        }
//        ViewBinding.root.visibility = View.VISIBLE
//        lifecycleOwner.currentState = hadHide.take(Lifecycle.State.RESUMED, Lifecycle.State.STARTED)
//        log.debug("onShow()")
//        onShow()
//    }
//    protected open fun onShow() {  }
//
//    fun hide() {
//        if (ViewBinding.root.visibility == View.GONE) {
//            return
//        }
//        ViewBinding.root.visibility = View.GONE
//        hadHide = true
//        log.debug("onHide()")
//        onHide()
//    }
//    protected open fun onHide() {  }
//
//    fun destroy() {
//        if (!created) return
//        WindowManagerService.removeView(ViewBinding.root)
//        lifecycleOwner.currentState = Lifecycle.State.DESTROYED
//        created = false
//        log.debug("onDestroy()")
//        onDestroy()
//    }
//    open fun onDestroy() { }
//
//
//    open fun onSetupLayoutParams(lp: WindowManager.LayoutParams) { }
//
//    @CallSuper
//    open fun recreate() {
//        val visibility = ViewBinding.root.visibility
//        this.destroy()
//        this.create()
//        ViewBinding.root.visibility = visibility
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    @CallSuper
//    open fun onSetupView() {
//        DRAG_VIEW?.setOnTouchListener(object : OnTouchListener {
//            private var lastX = 0f
//            private var lastY = 0f
//            private var downX = 0f
//            private var downY = 0f
//            override fun onTouch(v: View, event: MotionEvent): Boolean {
//                log.debug("overlay onTouch(event: ${actions[event.action] ?: "ACTION_UNKNOWN"})")
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        lastX = event.rawX
//                        lastY = event.rawY
//                        downX = event.rawX
//                        downY = event.rawY
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        LayoutParams.x += (event.rawX - lastX).toInt()
//                        LayoutParams.y += (event.rawY - lastY).toInt()
//                        WindowManagerService.updateViewLayout(ViewBinding.root, LayoutParams)
//                        lastX = event.rawX
//                        lastY = event.rawY
//                        onOverlayDrag(lastX, lastY)
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        if ((event.rawX - downX).absoluteValue < 10 &&
//                            (event.rawY - downY).absoluteValue < 10) {
//                            v.performClick()
//                        }
//                    }
//                }
//                return true
//            }
//        })
//    }
//
//    protected open fun onOverlayDrag(x: Float, y: Float) { }
//
//    protected open val DRAG_VIEW: View? = null
//
//    private val actions: Map<Int, String> = hashMapOf<Int, String>().also {
//        MotionEvent::class.java.fields.filter {
//            return@filter it.name.startsWith("ACTION_")
//        }.forEach { field ->
//            it[field.getInt(null)] = field.name
//        }
//    }
//
//    private val lifecycleOwner: LifecycleRegistry by lazy {
//        LifecycleRegistry(this)
//    }
//    override fun getLifecycle() = lifecycleOwner
//
//    companion object {
//        inline fun <VB: ViewBinding, reified T: BaseOverlayWidget<VB>?> create(context: Context): T {
//            return T::class.java
//                .getConstructor(Context::class.java)
//                .newInstance(Orientation.wrappedContext(context))
//        }
//    }
}