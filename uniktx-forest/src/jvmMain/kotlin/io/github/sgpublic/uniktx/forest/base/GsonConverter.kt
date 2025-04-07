package io.github.sgpublic.uniktx.common.base.forest

import com.dtflys.forest.converter.json.ForestGsonConverter
import com.dtflys.forest.exceptions.ForestConvertException
import io.github.sgpublic.uniktx.common.core.util.fromGson
import io.github.sgpublic.uniktx.common.core.util.toGson
import java.lang.reflect.Type

/**
 *
 * @author Madray Haven
 * @date 2022/10/20 16:28
 */
object GsonConverter: ForestGsonConverter() {
    /** 日期格式  */
    private var dateFormat: String? = null
    override fun getDateFormat(): String {
        return dateFormat!!
    }

    override fun setDateFormat(dateFormat: String) {
        this.dateFormat = dateFormat
    }

    override fun <T> convertToJavaObject(source: String, targetType: Type): T {
        try {
            return targetType.fromGson(source)
        } catch (e: Exception) {
            throw ForestConvertException(this, e)
        }
    }

    override fun encodeToString(obj: Any): String {
        return obj.toGson()
    }

    private val clazz = HashMap<String, Any>().javaClass
    override fun convertObjectToMap(obj: Any?): Map<String, Any>? {
        if (obj == null) {
            return null
        }
        if (obj is Map<*, *>) {
            val newMap: MutableMap<String, Any> = HashMap(obj.size)
            for (key in obj.keys) {
                val `val` = obj[key]
                if (`val` != null) {
                    newMap[key.toString()] = `val`
                }
            }
            return newMap
        }
        if (obj is CharSequence) {
            return convertToJavaObject(obj.toString(), clazz)
        }
        return clazz.fromGson(obj.toGson())
    }
}
