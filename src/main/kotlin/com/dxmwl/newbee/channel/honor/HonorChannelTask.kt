package com.dxmwl.newbee.channel.honor

import com.dxmwl.newbee.channel.ChannelTask
import com.dxmwl.newbee.channel.MarketInfo
import com.dxmwl.newbee.log.AppLogger
import com.dxmwl.newbee.util.ApkInfo
import java.io.File
import kotlin.math.roundToInt

class HonorChannelTask : ChannelTask() {

    override val channelName: String = "荣耀"

    override val fileNameIdentify: String = "HONOR"

    override val paramDefine: List<Param> = listOf(
        CLIENT_ID,
        CLIENT_SECRET,
    )
    private var clientId = ""

    private var clientSecret = ""

    private val connectClient = HonorConnectClient()

    override fun init(params: Map<Param, String?>) {
        clientId = params[CLIENT_ID] ?: ""
        clientSecret = params[CLIENT_SECRET] ?: ""
    }

    override suspend fun performUpload(file: File, apkInfo: ApkInfo, updateDesc: String, progress: (Int) -> Unit) {
        connectClient.uploadApk(file, apkInfo, clientId, clientSecret, updateDesc) {
            progress((it * 100).roundToInt())
        }
    }

    override suspend fun getMarketState(applicationId: String): MarketInfo {
        val appInfo = connectClient.getReviewState(clientId, clientSecret, applicationId)
        AppLogger.info(channelName, "appInfo:$appInfo")
        return appInfo.toMarketState()
    }

    companion object {
        private val CLIENT_ID = Param("client_id", desc = "客户端ID")
        private val CLIENT_SECRET = Param("client_secret", desc = "秘钥")
    }

}