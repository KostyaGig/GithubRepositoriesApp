package com.zinoview.githubrepositories.data.repositories.cache

import com.zinoview.githubrepositories.core.Abstract

/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
class CacheGithubRepositoryMapper : Abstract.RepositoryMapper<CacheGithubRepository> {

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ): CacheGithubRepository
        = CacheGithubRepository(name,private,language,owner,urlRepository, defaultBranch, isCollapsed)
}