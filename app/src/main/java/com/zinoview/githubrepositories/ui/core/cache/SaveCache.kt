package com.zinoview.githubrepositories.ui.core.cache

import com.zinoview.githubrepositories.data.core.GithubDao
import com.zinoview.githubrepositories.core.Save
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.repositories.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CacheGithubUserMapper
import com.zinoview.githubrepositories.ui.users.UiGithubUserState


/**
 * @author Zinoview on 27.08.2021
 * k.gig@list.ru
 */
interface SaveCache<T : CommunicationModel> : Save<List<T>> {

    class User(
        private val dao: GithubDao,
        private val mapper: CacheGithubUserMapper
    ) : SaveCache<UiGithubUserState> {

        override fun saveData(data: List<UiGithubUserState>) {
            val cacheGithubUsers = data.map { it.map(mapper) }
            cacheGithubUsers.map {
                dao.insertUser(it)
            }
        }
    }

    class Repository(
        private val dao: GithubDao,
        private val mapper: CacheGithubRepositoryMapper
    ): SaveCache<UiGithubRepositoryState> {

        override fun saveData(data: List<UiGithubRepositoryState>) {
            val cacheGithubRepositories = data.map { it.map(mapper) }
            cacheGithubRepositories.map {
                dao.insertRepository(it)
            }
        }
    }

}