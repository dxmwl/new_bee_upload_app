package com.dxmwl.newbee.channel.honor

import com.dxmwl.newbee.channel.checkApiSuccess
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = false)
data class HonorResult<T>(
    @Json(name = "code")
    val code: Int,
    @Json(name = "msg")
    val msg: String,
    @Json(name = "data")
    val data: T?
) {
    fun throwOnFail(action: String) {
        checkApiSuccess(code, 0, action, msg)
    }
}