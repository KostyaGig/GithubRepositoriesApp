package com.zinoview.githubrepositories.data.users.cloud

import com.zinoview.githubrepositories.data.users.GithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubCloudDataSource : GithubDataSource<Single<CloudGithubUser>> {

    class Base(
        private val githubService: GithubService
        ) : GithubCloudDataSource {

        override fun fetchData(param: String): Single<CloudGithubUser>
            = githubService.user(param)
    }
}