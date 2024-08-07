package com.dxmwl.newbee.channel.pugongying

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class CosTokenResp(
    val code: Int,
    val message: String,
    val data: CosToken
)

@JsonClass(generateAdapter = false)
data class CosToken(
    @Json(name = "key")
    val key: String,
    @Json(name = "endpoint")
    val endpoint: String,
    @Json(name = "params")
    val params: Map<String, String>,
)
