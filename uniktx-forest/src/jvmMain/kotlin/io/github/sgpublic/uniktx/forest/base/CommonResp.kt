package io.github.sgpublic.uniktx.common.base.forest

abstract class CommonResp<T>(
    val code: Int = 200,
    val message: String = "success.",
    val data: T? = null
)