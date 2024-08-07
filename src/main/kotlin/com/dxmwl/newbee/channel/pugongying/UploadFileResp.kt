package com.dxmwl.newbee.channel.pugongying

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class UploadFileResp(
    val code: Int,
    val message: String,
)
