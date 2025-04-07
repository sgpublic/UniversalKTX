package io.github.sgpublic.uniktx.common.core.forest

import com.dtflys.forest.exceptions.ForestRuntimeException
import com.dtflys.forest.http.ForestRequest
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

/**
 * @author Madray Haven
 * @date 2023/1/10 9:11
 */

interface RequestCallback<DataT> {
    /**
     * 当接口请求出错或 onResponse 方法调用期间抛错时调用此方法
     * @param e 错误封装
     */
    fun onException(e: UniktxForestException) {}

    /**
     * 相应回调，此方法不在主线程内调用，请勿直接在此方法内操作 UI
     * @param data 相应体
     * @throws UniktxForestException 相应回调抛错可封装为 UniktxForestException 抛出，将传入至 onException 方法
     * @see UniktxForestException
     */
    @Throws(UniktxForestException::class)
    fun onResponse(data: DataT) {
    }
}

private val executor = Executors.newSingleThreadExecutor()

fun <T> ForestRequest<T>.doSync(): T {
    return try {
        @Suppress("UNCHECKED_CAST")
        execute() as T
    } catch (e: ForestRuntimeException) {
        LoggerFactory.getLogger(javaClass)
            .warn("接口调用出错", e)
        throw e.toUniktxForestException()
    }
}

fun <T> ForestRequest<T>.doAsync(callback: RequestCallback<T>? = null) {
    executor.execute {
        val resp: T = try {
            doSync()
        } catch (e: UniktxForestException) {
            callback?.onException(e)
            return@execute
        }
        try {
            callback?.onResponse(resp)
        } catch (e: Exception) {
            LoggerFactory.getLogger(javaClass)
                .error("回调响应抛错", e)
            if (e is UniktxForestException) {
                callback!!.onException(e)
            } else {
                callback!!.onException(e.toUniktxForestException())
            }
        }
    }
}