package io.github.sgpublic.android

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.sgpublic.android.core.sysservice.sysEthernetManager

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EthernetManagerTest {
    private val TAG: String = "EthernetManagerTest"

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val service = appContext.getSystemService(
//            Context::class.java
//                .getField("ETHERNET_SERVICE")
//                .get(null) as String
//        )
        val clazz = Class.forName("android.net.EthernetManager")
        for (declaredMethod in clazz.declaredMethods) {
            Log.d(TAG, "declaredMethod: ${declaredMethod.name}")
        }
    }
}