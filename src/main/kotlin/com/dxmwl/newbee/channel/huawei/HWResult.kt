package com.dxmwl.newbee.channel.huawei

import com.dxmwl.newbee.channel.checkApiSuccess
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class HWResp(
    @Json(name = "ret")
    val result: HWResult
)

@JsonClass(generateAdapter = false)
data class HWResult(
    @Json(name = "code")
    val code: Int,
    @Json(name = "msg")
    val msg: String
) {

    fun throwOnFail(action: String) {
        checkApiSuccess(code, 0, action, msg)
    }
}