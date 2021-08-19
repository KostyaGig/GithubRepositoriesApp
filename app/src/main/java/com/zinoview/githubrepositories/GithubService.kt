package com.zinoview.githubrepositories

import io.reactivex.Single
import retrofit2.http.GET


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubService {

    @GET("/users/KostyaGig/repos")
    fun reposUser() : Single<List<CloudRepository>>
}