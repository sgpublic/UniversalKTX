package io.github.sgpublic.uniktx.common

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

/**
 * @author Madray Haven
 * @Date 2023/5/24 15:07
 */

operator fun AtomicBoolean.getValue(thisRef: Any?, property: KProperty<*>): Boolean {
    return get()
}
operator fun AtomicBoolean.setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
    return set(value)
}

operator fun AtomicInteger.getValue(thisRef: Any?, property: KProperty<*>): Int {
    return get()
}
operator fun AtomicInteger.setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
    return set(value)
}

operator fun AtomicLong.getValue(thisRef: Any?, property: KProperty<*>): Long {
    return get()
}
operator fun AtomicLong.setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
    return set(value)
}

operator fun <T: Any> AtomicReference<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
    return get()
}
operator fun <T: Any> AtomicReference<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    return set(value)
}
