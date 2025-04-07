package io.github.sgpublic.uniktx.common.core.forest

import com.dtflys.forest.exceptions.ForestNetworkException
import com.dtflys.forest.exceptions.ForestRuntimeException
import com.google.gson.JsonElement
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * @author Madray Haven
 * @date 2022/12/1 13:48
 */
class UniktxForestException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: JsonElement?) : super(if (message == null || message.isJsonNull) "未知错误" else message.asString)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}

fun Exception.toUniktxForestException(): UniktxForestException {
    when (this) {
        is ForestNetworkException -> {
            return UniktxForestException("网络错误：$statusCode", this)
        }

        is ForestRuntimeException -> {
            when (cause) {
                is SSLException -> return UniktxForestException("SSL 握手错误，请确认 SSL 证书可用性")
                is UnknownHostException -> return UniktxForestException("请检查网络连接")
            }
        }

        is ClassCastException -> {
            return UniktxForestException("疑似服务器错误，请联系管理员", this)
        }
    }
    return UniktxForestException("未知错误", this)
}