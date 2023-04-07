package io.github.sgpublic.kotlin.base.forest

class ResultResp<T>(
    code: Int, message: String
) : CommonResp<T>(code, message)