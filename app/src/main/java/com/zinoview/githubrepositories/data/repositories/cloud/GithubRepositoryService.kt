package com.zinoview.githubrepositories.data.repositories.cloud

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Streaming


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 * BASE URL https://api.github.com/
 */
interface GithubRepositoryService {

    @GET("users/{username}/repos")
    fun repositories(@Path("username") owner: String) : Single<List<CloudGithubRepository>>

    @GET("repos/{username}/{repo}")
    fun repository(@Path("username") owner: String, @Path("repo") repo: String) : Single<CloudGithubRepository>
}