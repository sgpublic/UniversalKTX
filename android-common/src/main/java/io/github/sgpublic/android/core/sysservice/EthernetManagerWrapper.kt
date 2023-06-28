package io.github.sgpublic.android.core.sysservice

import android.annotation.SuppressLint
import android.net.StaticIpConfiguration
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author Madray Haven
 * @Date 2023/6/28 10:47
 */
class EthernetManagerWrapper internal constructor(
    private val ethernet: Any
) {
    @SuppressLint("PrivateApi")
    private val EthernetManagerClass: Class<*> =
        Class.forName("android.net.EthernetManager")

    @get:RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @set:RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var configuration: StaticIpConfiguration
        get() = EthernetManagerClass.getMethod("getStaticIpConfiguration")
            .invoke(ethernet) as StaticIpConfiguration
        set(value) {
            EthernetManagerClass.getMethod("setStaticIpConfiguration", StaticIpConfiguration::class.java)
                .invoke(ethernet, value)
        }
}