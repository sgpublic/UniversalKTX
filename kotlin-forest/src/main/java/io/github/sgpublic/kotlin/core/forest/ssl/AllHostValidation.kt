package io.github.sgpublic.kotlin.core.forest.ssl

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * @author Madray Haven
 * @Date 2023/5/8 17:22
 */
object AllHostValidation: HostnameVerifier {
    override fun verify(hostname: String, session: SSLSession): Boolean {
        return true
    }
}