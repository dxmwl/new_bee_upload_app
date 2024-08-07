package com.dxmwl.newbee.channel.pugongying

import com.dxmwl.newbee.channel.MarketInfo
import com.dxmwl.newbee.channel.ReviewState
import com.dxmwl.newbee.channel.checkApiSuccess
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PugongyingAppInfoResp(
    val code: Int,
    val message: String,
    val data: PugongyingAppInfo,
) {

    fun throwOnFail(action: String) {
        checkApiSuccess(code, 0, action, message)
    }
}

@JsonClass(generateAdapter = false)
data class PugongyingAppInfo(
    /**
     * 1 草稿
     * 2 待审核
     * 3 审核通过
     * 4 审核不通过
     */
    var reviewStatus: Int = 0,

    /**
     * Build Key是唯一标识应用的索引ID
     */
    @Json(name = "buildKey")
    val buildKey: String,
    /**
     * 版本号, 默认为1.0 (是应用向用户宣传时候用到的标识，例如：1.1、8.2.1等。)
     */
    @Json(name = "buildVersion")
    val buildVersion: String,
    /**
     * 	上传包的版本编号，默认为1 (即编译的版本号，一般来说，编译一次会变动一次这个版本号, 在 Android 上叫 Version Code。对于 iOS 来说，是字符串类型；对于 Android 来说是一个整数。例如：1001，28等。)
     */
    @Json(name = "buildVersionNo")
    val buildVersionNo: String,
    /**
     * 	应用程序包名，iOS为BundleId，Android为包名
     */
    @Json(name = "buildIdentifier")
    val buildIdentifier: String
) {
    fun toMarketState(): MarketInfo {
        reviewStatus = 3
        val state = when (reviewStatus) {
            2 -> ReviewState.UnderReview
            3 -> ReviewState.Online
            4 -> ReviewState.Rejected
            else -> ReviewState.Unknown
        }
        return MarketInfo(
            reviewState = state,
            lastVersionCode = buildVersionNo.toLong(),
            lastVersionName = buildVersion
        )
    }
}
