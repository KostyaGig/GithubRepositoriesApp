package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.cloud.GithubService
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserRepository {

    fun user(query: String): Single<DataGithubUser>

    class Base(
        private val githubService: GithubService,
        private val mapper: Abstract.UserMapper<DataGithubUser>
    ) : GithubUserRepository {

        override fun user(query: String): Single<DataGithubUser> {
            return githubService.user(query).flatMap { cloudGithubUser ->
                Single.just(cloudGithubUser.map(mapper))
            }
        }
    }

}