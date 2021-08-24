package com.zinoview.githubrepositories.data.users.cloud

import com.zinoview.githubrepositories.data.core.GithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserCloudDataSource : GithubDataSource<CloudGithubUser,Unit>{

    class Base(
        private val githubService: GithubUserService
        ) : GithubUserCloudDataSource {

        override fun fetchData(param: String): Single<CloudGithubUser>
            = githubService.user(param)

        override fun saveData(data: Unit)
            = throw IllegalStateException("GithubUserCloudDataSource not use it method")
    }
}