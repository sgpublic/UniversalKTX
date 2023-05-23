package io.github.sgpublic.kotlin.core.util

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type
import kotlin.reflect.KClass

var GSON: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .disableHtmlEscaping()
    .create()

@Throws(GsonException::class)
fun <T: Any> KClass<T>.fromGson(src: String): T {
    return try {
        GSON.fromJson(src, this.java)
    } catch (e: JsonSyntaxException) {
        throw GsonException(GSON.fromJson(src, JsonElement::class.java), e)
    }
}

@Throws(GsonException::class)
fun <T: Any> Type.fromGson(src: String): T {
    return try {
        GSON.fromJson(src, this)
    } catch (e: JsonSyntaxException) {
        throw GsonException(GSON.fromJson(src, JsonElement::class.java), e)
    }
}

@Throws(GsonException::class)
fun <T: Any> KClass<T>.fromGson(src: JsonElement): T {
    return try {
        GSON.fromJson(src, this.java)
    } catch (e: JsonSyntaxException) {
        throw GsonException(src, e)
    }
}

fun Any?.toGson(): String {
    return GSON.toJson(this)
}

class GsonException(
    val element: JsonElement,
    cause: Throwable? = null
): JsonParseException("对象序列化失败", cause)

fun JsonObject.getBoolean(name: String, def: Boolean = false): Boolean {
    return get(name)?.takeIf { it.isJsonPrimitive }
        ?.asBoolean ?: def
}

fun JsonObject.take(name: String): JsonElement? {
    return this[name]?.takeIf { !it.isJsonNull }
}