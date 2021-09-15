package com.zinoview.githubrepositories.data.repositories.download.cloud

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Streaming


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
interface GithubDownloadRepoService {

    @Streaming
    @Headers("Accept: application/vnd.github.v3.raw")
    @GET("repos/{owner}/{repo}/zipball")
    fun downloadRepository(@Path("owner") owner: String, @Path("repo") repo: String) : Single<ResponseBody>

}