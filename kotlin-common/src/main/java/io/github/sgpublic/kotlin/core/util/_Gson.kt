package io.github.sgpublic.kotlin.core.util

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.reflect.KClass

private val GSON: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .disableHtmlEscaping()
    .create()

fun <T: Any> KClass<T>.fromGson(src: String): T {
    return GSON.fromJson(src, this.java)
        ?: throw GsonException(GSON.fromJson(src, JsonObject::class.java))
}

fun <T: Any> Type.fromGson(src: String): T {
    return GSON.fromJson(src, this)
        ?: throw GsonException(GSON.fromJson(src, JsonObject::class.java))
}

fun <T: Any> KClass<T>.fromGson(src: JsonElement): T {
    return GSON.fromJson(src, this.java)
        ?: throw GsonException(src)
}

fun Any?.toGson(): String {
    return GSON.toJson(this)
}

class GsonException(val element: JsonElement): Exception("对象序列化失败")

fun JsonObject.getBoolean(name: String, def: Boolean = false): Boolean {
    return get(name)?.takeIf { it.isJsonPrimitive }
        ?.asBoolean ?: def
}

fun JsonObject.take(name: String): JsonElement? {
    return this[name]?.takeIf { !it.isJsonNull }
}