package com.zinoview.githubrepositories.data.users.cloud

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 * BASE URL https://api.github.com/
 */
interface GithubUserService {

    @GET("users/{username}")
    fun user(@Path("username") query: String) : Single<CloudGithubUser>
}