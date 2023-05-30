package io.github.sgpublic.kotlin.core.forest.ssl

import com.dtflys.forest.http.ForestRequest
import com.dtflys.forest.ssl.SSLSocketFactoryBuilder
import com.dtflys.forest.ssl.TrustAllManager
import java.security.SecureRandom
import javax.net.ssl.SSLContext

/**
 * @author Madray Haven
 * @Date 2023/5/9 10:37
 */
object AllSSLSocketFactoryBuilder: SSLSocketFactoryBuilder {
    val TrustAllManager by lazy { TrustAllManager() }

    override fun getSSLSocketFactory(
        request: ForestRequest<*>?,
        protocol: String?
    ) = getSSLSocketFactory()

    fun getSSLSocketFactory() = SSLContext.getInstance("SSL").also {
        it.init(null, arrayOf(TrustAllManager), SecureRandom())
    }.socketFactory
}