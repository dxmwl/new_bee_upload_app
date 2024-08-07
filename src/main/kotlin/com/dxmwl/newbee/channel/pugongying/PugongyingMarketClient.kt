package com.dxmwl.newbee.channel.pugongying

import com.dxmwl.newbee.log.AppLogger
import com.dxmwl.newbee.log.action
import com.dxmwl.newbee.util.ApkInfo
import com.dxmwl.newbee.util.ProgressBody
import com.dxmwl.newbee.util.ProgressChange
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File


class PugongyingMarketClient {

    private val connectApi = PugongyingMarketApi()

    /**
     * "获取App信息"
     */
    suspend fun getAppInfo(apiKey: String, appKey: String): PugongyingAppInfo =
        AppLogger.action(LOG_TAG, "获取App信息") {
            val result = connectApi.getAppInfo(apiKey, appKey)
            result.throwOnFail("获取App信息")
            checkNotNull(result.data)
        }

    /**
     * 上传APK
     */
    suspend fun uploadApk(
        file: File,
        apkInfo: ApkInfo,
        apiKey: String,
        appKey: String,
        updateDesc: String,
        progressChange: ProgressChange
    ): Unit = AppLogger.action(LOG_TAG, "提交新版本") {
        val rawToken = getCosToken(apiKey,updateDesc)
        uploadFile(file, rawToken, progressChange)
    }

    private suspend fun getCosToken(apiKey: String, updateDesc: String): CosToken = AppLogger.action(LOG_TAG, "获取token") {
        val result = connectApi.getCosToken(apiKey, updateDesc,"android").data
        checkNotNull(result)
    }

    /**
     * 上传文件
     */
    private suspend fun uploadFile(
        file: File,
        cosToken: CosToken,
        progressChange: ProgressChange
    ): Unit = AppLogger.action(LOG_TAG, "上传Apk文件") {
        AppLogger.info(LOG_TAG,"上传地址：${cosToken.endpoint}")
        val contentType = "multipart/form-data".toMediaTypeOrNull() // 使用 toMediaTypeOrNull
        val apkBody = ProgressBody(contentType!!, file, progressChange)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, apkBody)
        val response = connectApi.uploadFile(
            cosToken.endpoint,
            cosToken.key,
            cosToken.params["signature"] ?: "",
            cosToken.params["x-cos-security-token"] ?: "",
            cosToken.params["x-cos-meta-file-name"] ?: "",
            body
        )
        if (response.code()==204){
            AppLogger.info("蒲公英分发API", "上传文件结果:成功")
        }else{
            AppLogger.info("蒲公英分发API", "上传文件结果:${response.message()}")
        }
    }


    companion object {
        private const val LOG_TAG = "蒲公英分发Api"
    }
}