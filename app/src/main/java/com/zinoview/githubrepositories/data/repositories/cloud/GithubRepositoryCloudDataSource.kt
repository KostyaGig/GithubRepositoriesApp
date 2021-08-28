package com.zinoview.githubrepositories.data.repositories.cloud

import com.zinoview.githubrepositories.data.core.GithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryCloudDataSource : GithubDataSource<List<CloudGithubRepository>,Unit> {

    fun repository(name: String,repo: String) : Single<CloudGithubRepository>

    class Base(
        private val githubRepositoryService: GithubRepositoryService
    ) : GithubRepositoryCloudDataSource {

        override fun repository(name: String, repo: String): Single<CloudGithubRepository>
            = githubRepositoryService.repository(name,repo)

        override fun fetchData(param: String): Single<List<CloudGithubRepository>>
            = githubRepositoryService.repositories(param)

        override fun saveData(data: Unit)
            = throw IllegalStateException("GithubRepositoryCloudDataSource not use it method")
    }
}