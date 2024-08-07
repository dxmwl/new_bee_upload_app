package com.dxmwl.newbee.channel.pugongying

import com.dxmwl.newbee.channel.ChannelTask
import com.dxmwl.newbee.channel.MarketInfo
import com.dxmwl.newbee.log.AppLogger
import com.dxmwl.newbee.util.ApkInfo
import java.io.File
import kotlin.math.roundToInt

class PugongyingChannelTask : ChannelTask() {
    override val channelName: String = "蒲公英"

    override val fileNameIdentify: String = "pugongying"

    override val paramDefine: List<Param> = listOf(
        API_KEY,
        APP_KEY,
    )

    private var marketClient = PugongyingMarketClient()

    private var apiKey = ""
    private var appKey = ""

    override fun init(params: Map<Param, String?>) {
        apiKey = params[API_KEY] ?: ""
        appKey = params[APP_KEY] ?: ""
    }

    override suspend fun performUpload(file: File, apkInfo: ApkInfo, updateDesc: String, progress: (Int) -> Unit) {
        marketClient.uploadApk(file, apkInfo, apiKey, appKey, updateDesc) {
            progress((it * 100).roundToInt())
        }
    }

    override suspend fun getMarketState(applicationId: String): MarketInfo {
        AppLogger.info(channelName, "appInfo")
        val appInfo = marketClient.getAppInfo(apiKey,appKey)
        AppLogger.info(channelName, "appInfo:$appInfo")
        return appInfo.toMarketState()
    }

    companion object {
        private val API_KEY = Param("apiKey", desc = "apiKey")
        private val APP_KEY = Param("appKey", desc = "appKey")
    }
}