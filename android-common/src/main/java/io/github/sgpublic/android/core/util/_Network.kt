package io.github.sgpublic.android.core.util

import java.net.NetworkInterface
import java.net.SocketException


/**
 * @author Madray Haven
 * @Date 2023/6/2 10:54
 */

val DeviceIpAddress: Set<String> get() {
    val ips = mutableSetOf<String>()
    try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val addresses = networkInterface.inetAddresses
            while (addresses.hasMoreElements()) {
                val inetAddress = addresses.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress.address.size == 4) {
                    ips.add(inetAddress.hostAddress ?: continue)
                }
            }
        }
    } catch (e: SocketException) {
        e.printStackTrace()
    }
    return ips
}
