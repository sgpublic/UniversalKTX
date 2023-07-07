@file:SuppressLint("NewApi", "PrivateApi", "DiscouragedPrivateApi")

package io.github.sgpublic.android.core.sysservice

import android.annotation.SuppressLint
import android.net.IpConfiguration
import android.net.LinkAddress
import android.net.StaticIpConfiguration
import java.net.InetAddress

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
    internal val conf: IpConfiguration = IpConfiguration::class.java.newInstance()
) {
    private val ipAssClass: Class<*> by lazy {
        val ipConf = Class.forName("android.net.IpConfiguration")
        for (clazz in ipConf.declaredClasses) {
            if (clazz.simpleName == "IpAssignment") {
                return@lazy clazz
            }
        }
        throw NoClassDefFoundError("android.net.IpConfiguration.IpAssignment")
    }

    var ipAssignment: IpAssignment
        get() = IpAssignment.valueOf(IpConfiguration::class.java.getDeclaredField(
            "ipAssignment"
        ).get(conf).toString())
        set(value) {
            IpConfiguration::class.java.getDeclaredField("ipAssignment").set(
                conf, ipAssClass.getMethod("valueOf", String::class.java)
                    .invoke(null, value.name)
            )
        }

    var staticIpConfiguration: StaticIpConfigurationWrapper?
        get() {
            return StaticIpConfigurationWrapper(
                IpConfiguration::class.java.getDeclaredField(
                    "staticIpConfiguration"
                ).get(conf) as StaticIpConfiguration? ?: return null
            )
        }
        set(value) {
            IpConfiguration::class.java
                .getDeclaredField("staticIpConfiguration")
                .set(conf, value?.conf)
        }

    enum class IpAssignment {
        STATIC,
        DHCP,
        UNASSIGNED;
    }
}

class StaticIpConfigurationWrapper(
    internal val conf: StaticIpConfiguration = StaticIpConfiguration::class.java.newInstance()
) {
    var ipAddress: LinkAddress
        get() = StaticIpConfiguration::class.java
            .getDeclaredField("ipAddress")
            .get(conf) as LinkAddress
        set(value) {
            StaticIpConfiguration::class.java
                .getDeclaredField("ipAddress")
                .set(conf, value)
        }

    var gateway: InetAddress?
        get() = StaticIpConfiguration::class.java
            .getDeclaredField("gateway")
            .get(conf) as InetAddress?
        set(value) {
            StaticIpConfiguration::class.java
                .getDeclaredField("gateway")
                .set(conf, value)
        }

    var dnsServers: ArrayList<InetAddress>
        get() = StaticIpConfiguration::class.java
            .getDeclaredField("dnsServers")
            .get(conf) as ArrayList<InetAddress>
        set(value) {
            StaticIpConfiguration::class.java
                .getDeclaredField("dnsServers")
                .set(conf, value)
        }
}