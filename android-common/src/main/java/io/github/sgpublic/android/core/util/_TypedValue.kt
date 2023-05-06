package io.github.sgpublic.android.core.util

import android.util.TypedValue
import io.github.sgpublic.android.Application

val Int.dp: Int get() = toFloat().dp.toInt()
val Float.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
    Application.ApplicationContext.resources.displayMetrics)

val Int.sp: Int get() = toFloat().sp.toInt()
val Float.sp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this,
    Application.ApplicationContext.resources.displayMetrics)