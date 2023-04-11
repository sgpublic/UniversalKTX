package io.github.sgpublic.kotlin.core.forest

import com.dtflys.forest.exceptions.ForestNetworkException
import com.dtflys.forest.exceptions.ForestRuntimeException
import com.google.gson.JsonElement
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
    if (this is ForestNetworkException) {
        return UniktxForestException("网络错误：$statusCode", this)
    } else if (this is ForestRuntimeException) {
        val cause = cause
        if (cause is SSLException) {
            return UniktxForestException("SSL 握手错误，请确认 SSL 证书可用性")
        }
    } else if (this is ClassCastException) {
        return UniktxForestException("疑似服务器错误，请联系管理员", this)
    }
    return UniktxForestException("未知错误", this)
}