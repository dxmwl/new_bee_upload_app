package com.dxmwl.newbee.page.version

import com.dxmwl.newbee.RetrofitFactory
import retrofit2.http.GET
import retrofit2.http.Path

fun GithubApi(): GithubApi {
    return RetrofitFactory.create("https://api.github.com/")
}

interface GithubApi {

    @GET("repos/{user}/{repo}/releases/latest")
    suspend fun getLastRelease(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): GithubRelease
}