package com.dxmwl.newbee.channel.pugongying

import com.dxmwl.newbee.RetrofitFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

fun PugongyingMarketApi(): PugongyingMarketApi {
    return RetrofitFactory.create("https://www.pgyer.com/apiv2/")
}

interface PugongyingMarketApi {

    /**
     * 获取token
     */
    @POST("app/getCOSToken")
    @FormUrlEncoded
    suspend fun getCosToken(
        @Field("_api_key")
        _api_key: String,
        @Field("buildUpdateDescription")
        buildUpdateDescription: String,
        @Field("buildType")
        buildType: String
    ): CosTokenResp

    /**
     * 获取App信息
     */
    @POST("app/view")
    @FormUrlEncoded
    suspend fun getAppInfo(
        @Field("_api_key")
        api_key: String,
        @Field("appKey")
        appKey: String
    ): PugongyingAppInfoResp

    /**
     * 上传文件
     */
    @POST()
    @Multipart
    suspend fun uploadFile(
        @Url url: String,
        @Query("key") key: String,
        @Query("signature") signature: String,
        @Query("x-cos-security-token") x_cos_security_token: String,
        @Query("x-cos-meta-file-name") x_cos_meta_file_name: String,
        @Part body: MultipartBody.Part
    ): Response<UploadFileResp>

}