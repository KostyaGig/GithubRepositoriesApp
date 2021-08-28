package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser


/**
 * @author Zinoview on 27.08.2021
 * k.gig@list.ru
 */
interface CacheGithubRepositoryMapper : Abstract.Mapper {

    fun map(isCollapsed: Boolean,uiGithubRepository: UiGithubRepository) : CacheGithubRepository

    class Base : CacheGithubRepositoryMapper {
        override fun map(isCollapsed: Boolean,uiGithubRepository: UiGithubRepository): CacheGithubRepository
            =  uiGithubRepository.mapTo(isCollapsed)
    }
}