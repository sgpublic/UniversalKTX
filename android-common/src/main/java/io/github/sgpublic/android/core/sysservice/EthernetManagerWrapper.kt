@file:SuppressLint("NewApi", "PrivateApi")

package io.github.sgpublic.android.core.sysservice

import android.annotation.SuppressLint
import android.net.IpConfiguration
import android.net.StaticIpConfiguration

/**
 * @author Madray Haven
 * @Date 2023/6/28 10:47
 */
class EthernetManagerWrapper internal constructor(
    private val ethernet: Any
) {
    private val EthernetManagerClass: Class<*> =
        Class.forName("android.net.EthernetManager")

    var configuration: IpConfigurationWrapper
        get() = IpConfigurationWrapper(
            EthernetManagerClass.getMethod("getConfiguration")
                .invoke(ethernet) as IpConfiguration
        )
        set(value) {
            EthernetManagerClass.getMethod("setConfiguration", IpConfiguration::class.java)
                .invoke(ethernet, value.conf)
        }
}

class IpConfigurationWrapper(
    internal val conf: IpConfiguration
) {
    private val ipAssClass: Class<*> by lazy {
        Class.forName("android.net.IpConfiguration.IpAssignment")
    }

    var ipAssignment: IpAssignment
        get() = IpAssignment.valueOf(ipAssClass.getField("ipAssignment").get(conf).toString())
        set(value) {
            ipAssClass.getMethod("valueOf", String::class.java)
                .invoke(null, value.name)
        }

    var staticIpConfiguration: StaticIpConfiguration
        get() = ipAssClass.getField("staticIpConfiguration").get(conf)
                as StaticIpConfiguration
        set(value) {
            ipAssClass.getField("staticIpConfiguration").set(conf, value)
        }

    enum class IpAssignment {
        STATIC,
        DHCP,
        UNASSIGNED;
    }
}
